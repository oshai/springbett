package io.github.oshai.springbett

import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class StadiumService(val repository: StadiumRepository) {
    fun getOne(id: Int): Stadium {
        return repository.findById(id).orElseThrow { Exception("stadium $id not found") }
    }

    fun getByFullName(name: String): Stadium {
        return repository.findAll().firstOrNull { it.name == name } ?: throw Exception("stadium $name not found")
    }

    fun getAll(): List<Stadium> {
        return repository.findAll().toList()
    }

    fun create(obj: Stadium): Stadium {
        return repository.save(obj.copy(stadiumId = null))
    }

    fun update(obj: Stadium): Stadium {
        if (!repository.existsById(obj.stadiumId!!)) {
            throw Exception("stadium ${obj.stadiumId} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }


}

fun LocalDateTime.toIso(): String {
    return this.atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT)
}

private val closeTimeMinutes: Long = 5

@Service
class DetailedGameService(
    private val gameService: GameService,
    private val gameResultRepository: GameResultRepository,
    private val pointStatsService: PointStatsService,
    private val teamService: TeamService,
    private val stadiumService: StadiumService,
    private val betService: BetService,
    private val tournamentService: TournamentService,
    private val userService: UserService,
) {

    fun getAll(): List<DetailedGame> {
        return gameService.getAll().map { game ->
            createDetailedGame(game.id())
        }
    }

    fun createDetailedGame(gameId: Int): DetailedGame {
        val game = gameService.getOne(gameId)
        return detailedGame(game)
    }

    private fun detailedGame(game: Game): DetailedGame {
        val result: GameResult? = gameResultRepository.findById(game.id()).orElseGet { null }
        val status = calcStatus(game, betService.getUserBetForGameOrNull(gameId = game.id()), result)
        return DetailedGame(
            gameId = game.id(),
            ratioWeight = game.ratioWeight,
            homeRatio = game.homeRatio,
            tieRatio = game.tieRatio,
            awayRatio = game.awayRatio,
            homeTeam = teamService.getOne(game.homeTeamId),
            awayTeam = teamService.getOne(game.awayTeamId),
            date = game.date.toString(),
            homeScore = result?.homeScore,
            awayScore = result?.awayScore,
            stadium = stadiumService.getOne(game.stadiumId),
            userHasBet = status.userHasBet,
            closeTime = status.closeTime,
            isOpen = status.isOpen,
            isPendingUpdate = status.isPendingUpdate,
            isBetResolved = status.isBetResolved,
        )
    }

    fun getOpenGames(): List<DetailedGame> {
        return getAll().filter { it.isOpen }
    }

    fun getTeamGames(teamId: Int): List<DetailedGame> {
        return getAll().filter { it.awayTeam.teamId == teamId || it.homeTeam.teamId == teamId }
    }

    fun getStadiumGames(stadiumId: Int): List<DetailedGame> {
        return getAll().filter { it.stadium.stadiumId == stadiumId }
    }

    fun createGame(game: DetailedGame): DetailedGame {
        return detailedGame(
            gameService.create(
                game.toGame()
            )
        )
    }

    fun getGame(gameId: Int): DetailedGame {
        return detailedGame(gameService.getOne(gameId))
    }

    fun updateGame(gameId: Int, game: DetailedGame): DetailedGame {
        val updatedGame = gameService.update(game.toGame(gameId))
        updateResult(gameId, game)
        return detailedGame(updatedGame)
    }

    // TODO special update for special users (they don't have a bet, or add a bet for them when creating a game)
    @Synchronized // TODO in transaction
    private fun updateResult(gameId: Int, game: DetailedGame) {
        if (game.homeScore == null || game.awayScore == null) {
            return
        }
        logger.info { "updating result" }
        val isNew = gameResultRepository.findById(gameId).isEmpty
        val gameResult = gameResultRepository.save(
            GameResult(
                gameId = gameId,
                homeScore = game.homeScore,
                awayScore = game.awayScore
            ).apply { if (isNew) this.setNew() }
        )
        val tournament = tournamentService.getOne(1)
        val updateIndex = tournament.currentPointsUpdate + 1
        val todayPointStats = userService.getAll().map { user ->
            val yesterdayPointStats: PointStats? = pointStatsService.getAll()
                .firstOrNull { it.userId == user.id() && it.pointsUpdateIndex == tournament.currentPointsUpdate }
            val userBet = betService.getUserBetForGameOrNull(gameId, user.username)
            val status = calcStatus(game.toGame(), userBet, gameResult)
            val todayPointStats = PointStats(
                pointsTimes100 = (yesterdayPointStats?.pointsTimes100 ?: 0) + (status.points.multiply(BigDecimal(100))
                    .toInt()),
                positionInTable = 0,
                results = (yesterdayPointStats?.results ?: 0) + if (status.resultWin) 1 else 0,
                marks = (yesterdayPointStats?.marks ?: 0) + if (status.gameMarkWin) 1 else 0,
                pointsUpdateIndex = updateIndex,
                userId = user.id(),
            )
            todayPointStats
        }
            .sortedByDescending { it.pointsTimes100 }
            .mapIndexed { index, stats ->
                stats.copy(positionInTable = index + 1)
            }
        todayPointStats.forEach {
            pointStatsService.createOrUpdate(it)
        }
    }

}

enum class Mark {
    Home,
    Tie,
    Away,
}

fun scoreToMark(homeScore: Int, awayScore: Int): Mark {
    return when {
        homeScore > awayScore -> Mark.Home
        homeScore < awayScore -> Mark.Away
        else -> Mark.Tie
    }
}

fun calcStatus(game: Game, userBet: Bet?, gameResult: GameResult?): GameState {
    val closeTime = game.date.minusMinutes(closeTimeMinutes)
    val closeTimeZoned = closeTime.atZone(ZoneId.of("UTC"))
    val isOpen = closeTimeZoned.isAfter(ZonedDateTime.now())
    val userHasBet = userBet != null
    val gameMark = gameResult?.let { scoreToMark(it.homeScore, it.awayScore) }
    val betMark = userBet?.let { scoreToMark(it.homeScore, it.awayScore) }
    var todayPoints = 0.0
    var mark = false
    var result = false
    if (gameResult != null && betMark != null && gameMark == betMark) {
        mark = true
        todayPoints += when {
            gameResult.homeScore > gameResult.awayScore -> game.homeRatio
            gameResult.homeScore < gameResult.awayScore -> game.awayRatio
            else -> game.tieRatio
        }.toDouble()
        todayPoints *= game.ratioWeight.toDouble();
        if (gameResult.homeScore == userBet.homeScore && gameResult.awayScore == userBet.awayScore) {
            result = true
            val totalGoals = gameResult.awayScore + gameResult.homeScore;
            todayPoints *= when {
                totalGoals < 2 -> 1.1
                totalGoals < 4 -> 1.2
                else -> 1.3
            }
        }
    }
    return GameState(
        isOpen = isOpen,
        isPendingUpdate = !isOpen && gameResult == null,
        isBetResolved = gameResult != null,
        closeTime = closeTime.toIso(),
        userHasBet = userHasBet,
        gameMarkWin = mark,
        resultWin = result,
        points = BigDecimal((todayPoints * 100).toInt()).divide(BigDecimal(100))
    )
}

data class GameState(
    val isOpen: Boolean,
    val isPendingUpdate: Boolean,
    val isBetResolved: Boolean,
    val userHasBet: Boolean,
    val closeTime: String,
    val gameMarkWin: Boolean,
    val points: BigDecimal,
    val resultWin: Boolean,
)

private fun DetailedGame.toGame(id: Int? = null) = Game(
    gameId = id ?: this.gameId,
    ratioWeight = this.ratioWeight,
    homeRatio = this.homeRatio,
    tieRatio = this.tieRatio,
    awayRatio = this.awayRatio,
    homeTeamId = this.homeTeam.teamId!!,
    awayTeamId = this.awayTeam.teamId!!,
    date = if (this.date.endsWith("Z"))
        ZonedDateTime.parse(this.date).toLocalDateTime()
    else
        LocalDateTime.parse(this.date),
    stadiumId = this.stadium.stadiumId!!,
)

data class DetailedGame(
    val gameId: Int,
    val ratioWeight: BigDecimal,
    val homeRatio: BigDecimal,
    val tieRatio: BigDecimal,
    val awayRatio: BigDecimal,
    val homeTeam: Team,
    val awayTeam: Team,
    val date: String, //": "2022-11-20T15:00:00Z",
    val homeScore: Int?,
    val awayScore: Int?,
    val stadium: Stadium,
    val closeTime: String,
    val userHasBet: Boolean = false,
    val isOpen: Boolean = true,
    val isPendingUpdate: Boolean = false,
    val isBetResolved: Boolean = false,
)

@Service
class GameService(val repository: GameRepository) {
    fun getOne(id: Int): Game {
        return repository.findById(id).orElseThrow { Exception("game $id not found") }
    }

    fun getAll(): List<Game> {
        return repository.findAll().toList()
    }

    fun create(obj: Game): Game {
        return repository.save(obj.copy(gameId = null))
    }

    fun update(obj: Game): Game {
        if (!repository.existsById(obj.gameId!!)) {
            throw Exception("game ${obj.gameId} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}


@Service
class TeamService(val repository: TeamRepository) {
    fun getOne(id: Int): Team {
        return repository.findById(id).orElseThrow { Exception("team $id not found") }
    }

    fun getByShortName(shortName: String): Team {
        return repository.findAll().firstOrNull { it.shortName == shortName }
            ?: throw Exception("team $shortName not found")
    }

    fun getByFullName(name: String): Team {
        return repository.findAll().firstOrNull { it.name == name } ?: throw Exception("team $name not found")
    }

    fun getAll(): List<Team> {
        return repository.findAll().toList()
    }

    fun create(obj: Team): Team {
        return repository.save(obj.copy(teamId = null))
    }

    fun update(obj: Team): Team {
        if (!repository.existsById(obj.teamId!!)) {
            throw Exception("team ${obj.teamId} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}

@Service
class TournamentService(val repository: TournamentRepository) {
    fun getOne(id: Int): Tournament {
        return repository.findById(id).orElseThrow { Exception("tournament $id not found") }
    }

    fun getAll(): List<Tournament> {
        return repository.findAll().toList()
    }

    fun create(obj: Tournament): Tournament {
        if (repository.existsById(obj.tournamentId)) {
            throw Exception("tournament ${obj.tournamentId} already exists")
        }
        return repository.save(obj)
    }

    fun update(obj: Tournament): Tournament {
        if (!repository.existsById(obj.tournamentId)) {
            throw Exception("tournament ${obj.tournamentId} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }

}

@Service
class PlayerService(val repository: PlayerRepository) {
    fun getOne(id: Int): Player {
        return repository.findById(id).orElseThrow { Exception("player $id not found") }
    }

    fun getAll(): List<Player> {
        return repository.findAll().toList()
    }

    fun create(obj: Player): Player {
        return repository.save(obj.copy(playerId = null))
    }

    fun update(obj: Player): Player {
        if (!repository.existsById(obj.playerId!!)) {
            throw Exception("player ${obj.playerId} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}

@Service
class GeneralBetService(
    private val repository: GeneralBetRepository,
    private val us: UserService,
) {
    fun getOne(id: Int): GeneralBet {
        return repository.findById(id).orElseThrow { Exception("GeneralBet $id not found") }
    }

    fun getAll(): List<GeneralBet> {
        return repository.findAll().toList()
    }

    fun getAllForView(): List<ViewGeneralBet> {
        return getAll().map { bet ->
            val user = us.getOne(bet.userId)
            ViewGeneralBet(
                generalBetId = bet.id(),
                goldenBootPlayerId = bet.goldenBootPlayerId,
                winningTeamId = bet.winningTeamId,
                ownerName = "${user.firstName} ${user.lastName}",
            )
        }
    }

    fun create(obj: CreateGeneralBet): GeneralBet {
        val username = getRequestUserName()
        val user = us.getOne(username)
        return repository.save(
            GeneralBet(
                winningTeamId = obj.winningTeamId,
                goldenBootPlayerId = obj.goldenBootPlayerId,
                userId = user.id(),
            )
        )
    }

    fun update(obj: GeneralBet): GeneralBet {
        if (!repository.existsById(obj.generalBetId!!)) {
            throw Exception("player ${obj.generalBetId} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }

    fun hasBet(username: String): Boolean {
        return getAll().any { us.getOne(it.userId).username == username }
    }

    fun getBetForUser(username: String): GeneralBet {
        val username = getRequestUserName()
        val user = us.getOne(username)
        return repository.findAll().first { it.userId == user.userId }
    }

    fun canSubmitBets(): Boolean {
        return true
    }
}

//data class ResolveGeneralBet(
//    val playerIsRight { get; set; }
//
//    public Boolean TeamIsRight { get; set; }
//}
data class ViewGeneralBet(
    val generalBetId: Int,
    val goldenBootPlayerId: Int,
    val winningTeamId: Int,
    val ownerName: String,
    val isResolved: Boolean = false,
    val isClosed: Boolean = false,
    val points: Int = 0,
    val closeTime: String = "2022-11-19T00:00:00Z",
)

data class CreateGeneralBet(
    val goldenBootPlayerId: Int,
    val winningTeamId: Int,
)


@Service
class BetService(
    private val br: BetRepository,
    private val us: UserService,
) {
    fun getUserBets(username: String): List<Bet> {
        val user = us.getOne(username)
        return br.findAll().filter { it.userId == user.userId }.map { bet ->
            Bet(
                betId = bet.id(),
                homeScore = bet.homeScore,
                awayScore = bet.awayScore,
                gameId = bet.gameId,
                userId = user.id(),
            )
        }
    }

    fun getUserBetForGameOrNull(gameId: Int): Bet? {
        return getUserBetForGameOrNull(gameId, getRequestUserName())
    }

    fun getUserBetForGameOrNull(gameId: Int, username: String): Bet? {
        val bets = getUserBets(username)
        return bets.firstOrNull { it.gameId == gameId }
    }

    fun create(obj: BetCreate): Bet {
        return br.save(
            Bet(
                gameId = obj.gameId,
                awayScore = obj.awayScore,
                homeScore = obj.homeScore,
                userId = us.getOne(getRequestUserName()).id()
            )
        )
    }
}


@Service
class DetailedBetService(
    private val br: BetRepository,
    private val us: UserService,
    private val gr: GameResultRepository,
    private val detailedGameService: DetailedGameService
) {
    fun getUserBets(username: String): List<DetailedBet> {
        val user = us.getOne(username)
        return br.findAll().filter { it.userId == user.userId }.map { bet ->
            val result: GameResult? = gr.findById(bet.gameId).orElseGet { null }
            createDetailedBet(bet.gameId, bet, user, result)
        }
    }

    private fun getUserBetForGameOrNull(gameId: Int): Bet? {
        val username = getRequestUserName()
        val user = us.getOne(username)
        return br.findAll().firstOrNull { it.userId == user.userId && it.gameId == gameId }
    }

    fun getUserBetForGame(gameId: Int): DetailedBet {
        val username = getRequestUserName()
        val user = us.getOne(username)
        val bet = getUserBetForGameOrNull(gameId)
        val result: GameResult? = gr.findById(gameId).orElseGet { null }
        return createDetailedBet(gameId, bet, user, result)
    }

    fun getBetsForGame(gameId: Int): List<DetailedBet> {
        val result: GameResult? = gr.findById(gameId).orElseGet { null }
        return br.findAll().filter { it.gameId == gameId }.map { bet ->
            val user = us.getOne(bet.userId)
            createDetailedBet(bet.gameId, bet, user, result)
        }
    }

    private fun createDetailedBet(
        gameId: Int,
        bet: Bet?,
        user: User,
        gameResult: GameResult?
    ): DetailedBet {
        val game = detailedGameService.createDetailedGame(gameId)
        val status = calcStatus(game.toGame(), bet, gameResult)
        return DetailedBet(
            betId = bet?.betId ?: -1,
            homeScore = bet?.homeScore,
            awayScore = bet?.awayScore,
            game = game,
            user = user,
            gameMarkWin = status.gameMarkWin,
            isOpenForBetting = status.isOpen,
            isResolved = status.isBetResolved,
            points = status.points.toDouble(),
            resultWin = status.resultWin,
        )
    }

}

data class DetailedBet(
    val betId: Int,
    val homeScore: Int?,
    val awayScore: Int?,
    val game: DetailedGame,
    val user: User,
    val gameMarkWin: Boolean = false,
    val isOpenForBetting: Boolean = true,
    val isResolved: Boolean = false,
    val points: Double = 0.0,
    val resultWin: Boolean = false,
)

data class BetCreate(
    val gameId: Int,
    val homeScore: Int,
    val awayScore: Int,
)

@Service
class UserService(
    val repository: UserRepository,
    val credRepository: CredRepository,
) {
    fun getOne(id: UUID): User {
        return repository.findById(id).orElseThrow { Exception("user $id not found") }
    }

    fun getOne(username: String): User {
        return getAll().firstOrNull { it.username == username } ?: throw Exception("iser $username not found")
    }

    fun getAll(): List<User> {
        return repository.findAll().toList()
    }

    fun create(obj: User): User {
        return repository.save(obj.copy(userId = null))
    }

    fun update(obj: User): User {
        if (!repository.existsById(obj.userId!!)) {
            throw Exception("player ${obj.userId} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }

    fun register(body: Registration) {
        if (body.password != body.confirmPassword) {
            throw Exception("this went wrong")
        }
        if (repository.findAll().any { it.username == body.username }) {
            throw Exception("User name already taken!")
        }
        val user = repository.save(
            User(
                username = body.username,
                firstName = body.firstname,
                lastName = body.lastname,
                email = body.email,
                isAdmin = false,
            )
        )
        val cred = Cred(user.id(), body.password).setNew()
        credRepository.save(cred)
    }

    fun makeAdmin(userId: UUID): User {
        return repository.save(getOne(userId).copy(isAdmin = true))
    }

    fun changePassword(change: ChangePassword) {
        if (change.newPassword != change.confirmPassword) {
            throw Exception("this went wrong")
        }
        val username = getRequestUserName()
        val user = getOne(username)
        val cred = credRepository.findById(user.id()).get()
        if (cred.pw != change.oldPassword) {
            throw Exception("this went wrong")
        }
        credRepository.save(cred.copy(pw = change.newPassword))
    }
}

@Service
class UserStatsService(
    val userService: UserService,
    val pointStatsService: PointStatsService
) {

    fun getAll(): List<UserStatsModel> {
        return userService.getAll().map { mapUser(it) }
    }

    fun getTable(): List<UserStatsModel> {
        return userService.getAll().map { mapUser(it) }
    }

    fun getUserStats(username: String): UserStatsModel {
        val user = userService.getOne(username)
        return mapUser(user)
    }

    private fun mapUser(
        user: User
    ): UserStatsModel {
        val userPointList = pointStatsService.getAll().filter { it.userId == user.userId }
        val userPointCurrent: PointStats? = userPointList.maxByOrNull { it.pointsUpdateIndex }
        val userPointsPrevious: PointStats? =
            userPointCurrent?.let { curr -> userPointList.firstOrNull { prev -> curr.pointsUpdateIndex - 1 == prev.pointsUpdateIndex } }
        val place = userPointCurrent?.positionInTable ?: 1
        val placePrev = userPointsPrevious?.positionInTable ?: place
        return UserStatsModel(
            username = user.username,
            name = "${user.firstName} ${user.lastName}",
            id = user.userId.toString(),
            email = user.email,
            isAdmin = user.isAdmin,
            points = userPointCurrent?.let { BigDecimal(it.pointsTimes100).divide(BigDecimal(100)) } ?: BigDecimal.ZERO,
            yesterdayPoints = userPointsPrevious?.let { BigDecimal(it.pointsTimes100).divide(BigDecimal(100)) }
                ?: BigDecimal.ZERO,
            place = place,
            placeDiff = placePrev - place,
            results = userPointCurrent?.results ?: 0,
            marks = userPointCurrent?.marks ?: 0,
            // totalMarks = 0,
        )
    }


}

data class UserStatsModel(
    val username: String,
    val name: String,
    val id: String,
    val email: String,
    val isAdmin: Boolean,
    val points: BigDecimal,
    val yesterdayPoints: BigDecimal,
    val place: Int,
    val placeDiff: Int,
    val results: Int,
    val marks: Int,
    // val totalMarks: Int,
    //val tournamentBet: Int,

)

@Service
class PointStatsService(private val repository: PointStatsRepository) {
    fun getOne(id: Int): PointStats {
        return repository.findById(id).orElseThrow { Exception("points stats $id not found") }
    }

    fun getAll(): List<PointStats> {
        return repository.findAll().toList()
    }

    fun create(obj: PointStats): PointStats {
        return repository.save(obj.copy(id = null))
    }

    fun createOrUpdate(obj: PointStats): PointStats {
        val current =
            getAll().firstOrNull() { it.userId == obj.userId && it.pointsUpdateIndex == obj.pointsUpdateIndex }
        return if (current != null) {
            update(obj.copy(id = current.id))
        } else {
            create(obj)
        }

    }

    fun update(obj: PointStats): PointStats {
        if (!repository.existsById(obj.id())) {
            throw Exception("point stats ${obj.id()} do not exists")
        }
        return repository.save(obj)
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}
