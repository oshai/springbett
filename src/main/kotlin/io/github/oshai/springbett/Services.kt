package io.github.oshai.springbett

import org.springframework.stereotype.Service
import java.math.BigDecimal
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
    val gameService: GameService,
    val teamService: TeamService,
    val stadiumService: StadiumService,
) {
    fun getAll(): List<DetailedGame> {
        return gameService.getAll().map { game ->
            DetailedGame(
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
        }
    }

}

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
class BetService(val br: BetRepository, val us: UserService) {
    fun getUserBets(username: String): List<Bet> {
        val user = us.getOne(username)
        return br.findAll().filter { it.userId == user.userId }
    }

    fun create(obj: BetCreate): Bet {
        return br.save(Bet(
            gameId = obj.gameId,
            awayScore = obj.awayScore,
            homeScore = obj.homeScore,
            userId = us.getOne(getRequestUser()).id()
        ))
    }
}

data class BetCreate(
    val gameId: Int,
    val homeScore: Int,
    val awayScore: Int,
)

@Service
class UserService(val repository: UserRepository) {
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
        place = 0,
        placeDiff = 0,
        result = 0,
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
    val result: Int,
    val marks: Int,
    val totalMarks: Int,
    //val tournamentBet: Int,

)