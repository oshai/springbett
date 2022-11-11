package io.github.oshai.springbett

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

interface StadiumRepository : CrudRepository<Stadium, Int>

@Table("stadium")
data class Stadium(
    @Id var id: Int? = null,
    val name: String,
    val shortName: String,
    val city: String,
    val capacity: Int
)

interface TeamRepository : CrudRepository<Team, Int>

@Table("team")
data class Team(@Id val id: Int? = null, val name: String, val shortName: String)

interface BetRepository : CrudRepository<Bet, Int>

@Table("bet")
data class Bet(@Id val id: Int? = null,
               val userId: UUID,
               val gameId: Int,
               val homeTeamScore: Int,
               val awayTeamScore: Int,
)

interface GameRepository : CrudRepository<Cred, UUID>

@Table("game")
data class Game(
    @Id val id: Int? = null,
    val stadiumId: Int,
    val homeTeamId: Int,
    val awayTeamId: Int,
    val startTime: ZonedDateTime,
    val ratioWeight: BigDecimal,
    val homeRatio: BigDecimal,
    val tieRatio: BigDecimal,
    val awayRatio: BigDecimal,
    val homeTeamScore: Int?,
    val awayTeamScore: Int?,
)
interface PlayerRepository : CrudRepository<Player, Int>

@Table("player")
data class Player(@Id val id: Int? = null, val name: String, val shortName: String)

interface UserRepository : CrudRepository<User, UUID>

@Table("users")
data class User(
    @Id val id: UUID? = null,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isAdmin: Boolean,
) {
    val roles: String get() {
        return if (isAdmin) {
            "Admin"
        } else {
            "Reader"
        }
    }
}

interface CredRepository : CrudRepository<Cred, UUID>

@Table("cred")
data class Cred(
    @Id val id: UUID? = null,
    val pw: String,
)
