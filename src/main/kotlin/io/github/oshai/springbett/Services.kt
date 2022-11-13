package io.github.oshai.springbett

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

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
        return repository.save(obj.copy(stadiumId = null))
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }


}

@Service
class DetailedGameService(
    private val gameService: GameService,
    private val teamService: TeamService,
    private val stadiumService: StadiumService,
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

    private fun detailedGame(game: Game) = DetailedGame(
        gameId = game.id(),
        ratioWeight = game.ratioWeight,
        homeRatio = game.homeRatio,
        tieRatio = game.tieRatio,
        awayRatio = game.awayRatio,
        homeTeam = teamService.getOne(game.homeTeamId),
        awayTeam = teamService.getOne(game.awayTeamId),
        date = game.startTime.toString(),
        homeScore = game.homeScore,
        awayScore = game.awayScore,
        stadium = stadiumService.getOne(game.stadiumId),
        userHasBet = false,
        closeTime = "2022-11-20T14:55:00Z",
        isOpen = true,
        isPendingUpdate = false,
        isBetResolved = false,
    )

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
        return detailedGame(
            gameService.update(
                game.toGame(gameId)
            )
        )
    }

}

private fun DetailedGame.toGame(id: Int? = null) = Game(
    gameId = id ?: this.gameId,
    ratioWeight = this.ratioWeight,
    homeRatio = this.homeRatio,
    tieRatio = this.tieRatio,
    awayRatio = this.awayRatio,
    homeTeamId = this.homeTeam.teamId!!,
    awayTeamId = this.awayTeam.teamId!!,
    startTime = ZonedDateTime.parse(this.date).toLocalDateTime(),
    homeScore = this.homeScore,
    awayScore = this.awayScore,
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
    val userHasBet: Boolean = false,
    val closeTime: String = "2022-11-20T14:55:00Z",
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
        return repository.save(obj.copy(gameId = null))
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
        return repository.save(obj.copy(teamId = null))
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
        return repository.save(obj.copy(playerId = null))
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
        return repository.save(obj.copy(generalBetId = null))
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
}

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
    private val detailedGameService: DetailedGameService
) {
    fun getUserBets(username: String): List<DetailedBet> {
        val user = us.getOne(username)
        return br.findAll().filter { it.userId == user.userId }.map { bet ->
            DetailedBet(
                betId = bet.id(),
                homeScore = bet.homeScore,
                awayScore = bet.awayScore,
                game = detailedGameService.createDetailedGame(bet.gameId),
                user = user,
            )
        }
    }

    fun getUserBetForGame(gameId: Int): DetailedBet {
        val username = getRequestUserName()
        val user = us.getOne(username)
        val bet = br.findAll().firstOrNull { it.userId == user.userId && it.gameId == gameId }
        if (bet == null) {
            return DetailedBet(
                betId = -1,
                homeScore = null,
                awayScore = null,
                game = detailedGameService.createDetailedGame(gameId),
                user = user,
            )
        } else {
            return DetailedBet(
                betId = bet.id(),
                homeScore = bet.homeScore,
                awayScore = bet.awayScore,
                game = detailedGameService.createDetailedGame(bet.gameId),
                user = user,
            )
        }
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

data class DetailedBet(
    val betId: Int,
    val homeScore: Int?,
    val awayScore: Int?,
    val game: DetailedGame,
    val user: User,
    val gameMarkWin: Boolean = false,
    val isOpenForBetting: Boolean = true,
    val isResolved: Boolean = false,
    val points: Int = 0,
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
        return repository.save(obj.copy(userId = null))
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
class UserStatsService(val users: UserService) {

    fun getAll(): List<UserStatsModel> {
        return users.getAll().map { mapUser(it) }
    }

    fun getTable(): List<UserStatsModel> {
        return users.getAll().map { mapUser(it) }
    }

    fun getUserStats(username: String): UserStatsModel {
        val user = users.getOne(username)
        return mapUser(user)
    }

    private fun mapUser(
        user: User
    ) = UserStatsModel(
        username = user.username,
        name = "${user.firstName} ${user.lastName}",
        id = user.userId.toString(),
        email = user.email,
        isAdmin = user.isAdmin,
        points = 0,
        yesterdayPoints = 0,
        place = 1,
        placeDiff = 0,
        results = 0,
        marks = 0,
        totalMarks = 0,
    )


}

data class UserStatsModel(
    val username: String,
    val name: String,
    val id: String,
    val email: String,
    val isAdmin: Boolean,
    val points: Int,
    val yesterdayPoints: Int,
    val place: Int,
    val placeDiff: Int,
    val results: Int,
    val marks: Int,
    val totalMarks: Int,
    //val tournamentBet: Int,

)