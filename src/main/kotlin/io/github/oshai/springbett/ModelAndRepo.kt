package io.github.oshai.springbett

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

interface StadiumRepository : CrudRepository<Stadium, Int>

@Table("stadium")
data class Stadium(
    val name: String,
    val shortName: String,
    val city: String,
    val capacity: Int,
    @Id var stadiumId: Int? = null,
) {
    fun id() = stadiumId!!
}

interface TeamRepository : CrudRepository<Team, Int>

@Table("team")
data class Team(
    val name: String, val shortName: String,
    @Id val teamId: Int? = null,
) {
    fun id() = teamId!!
}

interface BetRepository : CrudRepository<Bet, Int>

@Table("bet")
data class Bet(
    @Id val betId: Int? = null,
    val userId: UUID,
    val gameId: Int,
    val homeScore: Int,
    val awayScore: Int,
) {
    fun id() = betId!!
}

interface GameRepository : CrudRepository<Game, Int>

@Table("game")
data class Game(
    val stadiumId: Int,
    val homeTeamId: Int,
    val awayTeamId: Int,
    val date: LocalDateTime, // start time
    val ratioWeight: BigDecimal,
    val homeRatio: BigDecimal,
    val tieRatio: BigDecimal,
    val awayRatio: BigDecimal,
    @Id val gameId: Int? = null,
) {
    fun id() = gameId!!
}

interface GameResultRepository : CrudRepository<GameResult, Int>

@Table("game_result")
data class GameResult(
    val homeScore: Int,
    val awayScore: Int,
    @Id val gameId: Int,
) : Persistable<Int> {

    @org.springframework.data.annotation.Transient
    private var newId: Boolean = false

    @org.springframework.data.annotation.Transient
    fun setNew(): GameResult {
        newId = true
        return this
    }

    override fun getId(): Int {
        return gameId
    }

    override fun isNew(): Boolean {
        return newId
    }
}

interface TournamentRepository : CrudRepository<Tournament, Int>

@Table("tournament")
data class Tournament(
    @Id val tournamentId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val currentPointsUpdate: Int,
) : Persistable<Int> {

    @org.springframework.data.annotation.Transient
    private var newId: Boolean = false

    @org.springframework.data.annotation.Transient
    fun setNew(): Tournament {
        newId = true
        return this
    }

    override fun getId(): Int {
        return tournamentId
    }

    override fun isNew(): Boolean {
        return newId
    }
}

interface TournamentResultRepository : CrudRepository<TournamentResult, Int>

@Table("tournament_result")
data class TournamentResult(
    @Id val tournamentId: Int,
    val winningTeamId: Int,
    val goldenBootPlayerId: Int,
)

interface PlayerRepository : CrudRepository<Player, Int>

@Table("player")
data class Player(val name: String, val shortName: String, @Id val playerId: Int? = null) {
    fun id() = playerId!!
}

interface UserRepository : CrudRepository<User, UUID>

@Table("users")
data class User(
    @Id val userId: UUID? = null,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isAdmin: Boolean,
) {
    val roles: String
        get() {
            return if (isAdmin) {
                "Admin"
            } else {
                "Reader"
            }
        }

    fun id() = userId!!
}

interface CredRepository : CrudRepository<Cred, UUID>

@Table("cred")
data class Cred(
    @Id val uuid: UUID,
    val pw: String,
) : Persistable<UUID> {

    @org.springframework.data.annotation.Transient
    private var newId: Boolean = false

    @org.springframework.data.annotation.Transient
    fun setNew(): Cred {
        newId = true
        return this
    }

    override fun getId(): UUID {
        return uuid
    }

    override fun isNew(): Boolean {
        return newId
    }
}

interface GeneralBetRepository : CrudRepository<GeneralBet, Int>

@Table("general_bet")
data class GeneralBet(
    val winningTeamId: Int,
    val goldenBootPlayerId: Int,
    val userId: UUID,
    @Id val generalBetId: Int? = null
) {
    fun id() = generalBetId!!
}

interface PointStatsRepository : CrudRepository<PointStats, Int>

@Table("point_stats") // each row is user stat in a specific point in time (pointsUpdateIndex)
data class PointStats(
    val pointsTimes100: Int,
    val positionInTable: Int,
    val results: Int,
    val marks: Int,
    val pointsUpdateIndex: Int,
    val userId: UUID,
    @Id val id: Int? = null
) {
    fun id() = id!!
}

