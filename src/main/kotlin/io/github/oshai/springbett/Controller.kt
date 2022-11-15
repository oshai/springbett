package io.github.oshai.springbett

import com.google.gson.Gson
import mu.KotlinLogging
import org.springframework.boot.web.server.ErrorPage
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.util.UUID


private val logger = KotlinLogging.logger {}

@RestController
class RedirectController {
    @GetMapping(
        "/bets_center",
        "/games/**",
        "/teams/**",
        "/stadiums/**",
        "/users/**",
        "/login",
        "/join",
        "/manage",
        "/manage_users",
    )
    fun forward() = ModelAndView("forward:/index.html")

    @GetMapping("/notFound")
    fun forwardNotFound() = ModelAndView("forward:/index.html")


    @Bean
    fun containerCustomizer(): WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
        return WebServerFactoryCustomizer { container: ConfigurableServletWebServerFactory ->
            container.addErrorPages(
                ErrorPage(
                    HttpStatus.NOT_FOUND,
                    "/notFound"
                )
            )
        }
    }
}

@RestController
class GameController(private val service: DetailedGameService, private val gameService: GameService) {
    @GetMapping("/api/games")
    fun getDetailedGames(): List<DetailedGame> {
        return service.getAll()
    }

    @GetMapping("/api/games/open")
    fun openGames(): List<DetailedGame> {
        return service.getOpenGames()
    }

    @GetMapping("/api/teams/{teamId}/games")
    fun teamGames(@PathVariable("teamId") teamId: Int): List<DetailedGame> {
        return service.getTeamGames(teamId)
    }

    @GetMapping("/api/games/Stadium/{stadiumId}")
    fun stadiumGames(@PathVariable("stadiumId") stadiumId: Int): List<DetailedGame> {
        return service.getStadiumGames(stadiumId)
    }


    @GetMapping("/api/games/{gameId}")
    fun getGame(@PathVariable("gameId") gameId: Int): DetailedGame {
        return service.getGame(gameId)
    }


    @PutMapping("/api/games/{gameId}")
    fun updateGame(@PathVariable("gameId") gameId: Int, @RequestBody body: DetailedGame): DetailedGame {
        return service.updateGame(gameId, body)
    }

    @PostMapping("/api/games")
    fun addGame(@RequestBody body: DetailedGame): DetailedGame {
        return service.createGame(body)
    }

    @DeleteMapping("/api/games/{gameId}")
    fun deleteGame(@PathVariable("gameId") gameId: Int) {
        return gameService.delete(gameId)
    }
}

@RestController
class UserManagementController(private val service: UserService) {
    @PostMapping("/api/account/register")
    fun register(@RequestBody body: String) {
        return service.register(Gson().fromJson(body, Registration::class.java))
    }

    @DeleteMapping("/api/users/{userId}")
    fun delete(@PathVariable("userId") userId: UUID) {
        return service.delete(userId)
    }

    @PostMapping("/api/users/makeadmin/{userId}")
    fun makeAdmin(@PathVariable("userId") userId: UUID): User {
        return service.makeAdmin(userId)
    }

    @PostMapping("/api/account/changePassword")
    fun changePassword(@RequestBody body: String) {
        return service.changePassword(Gson().fromJson(body, ChangePassword::class.java))
    }
}

data class ChangePassword(
    val confirmPassword: String,
    val newPassword: String,
    val oldPassword: String,
)

data class Registration(
    val username: String,
    val confirmPassword: String,
    val password: String,
    val email: String,
    val firstname: String,
    val lastname: String,
)

@RestController
class GeneralBetController(private val service: GeneralBetService) {

    @GetMapping("/api/generalbets/has-bet/{username}")
    fun hasBet(@PathVariable("username") username: String) = service.hasBet(username)

    @GetMapping("/api/generalbets/user/{username}")
    fun getBet(@PathVariable("username") username: String) = service.getBetForUser(username)

    @GetMapping("/api/generalbets/cansubmitbets/")
    fun canSubmitBets() = service.canSubmitGeneralBets()

    @GetMapping("/api/generalbets")
    fun getAll() = service.getAllForView()

    @PostMapping("/api/generalbets")
    fun create(@RequestBody body: CreateGeneralBet) = service.create(body)

    @PutMapping("/api/generalbets/{generalBetId}")
    fun update(
        @PathVariable("generalBetId") generalBetId: Int,
        @RequestBody body: GeneralBet
    ) = service.update(body.copy(generalBetId = generalBetId))

    @PutMapping("/api/generalbets/{generalBetId}/resolve")
    fun resolveBet(
        @PathVariable("generalBetId") generalBetId: Int,
        @RequestBody body: ResolveGeneralBetRequest
    ) = service.resolveBet(generalBetId, body)
}

@RestController
class BetController(
    private val service: DetailedBetService,
    private val betService: BetService,
) {

    @GetMapping("/api/bets/user/{username}")
    fun getUserBets(@PathVariable("username") username: String) = service.getUserBets(username)

    @GetMapping("/api/games/{gameId}/mybet")
    fun getUserBetForGame(@PathVariable("gameId") gameId: Int) = service.getUserBetForGame(gameId)

    @GetMapping("/api/games/{gameId}/bets")
    fun getBetsForGame(@PathVariable("gameId") gameId: Int) = service.getBetsForGame(gameId)

    @PostMapping("/api/bets/")
    fun create(@RequestBody body: BetCreate) = betService.create(body)

}

@RestController
class StadiumController(private val service: StadiumService) {

    @GetMapping("/api/stadiums")
    fun getAll() = service.getAll()

    @GetMapping("/api/stadiums/{id}")
    fun getOne(@PathVariable("id") id: Int) = service.getOne(id)

    @PostMapping("/api/stadiums/create")
    fun create(@RequestBody body: Stadium) = service.create(body)

    @PutMapping("/api/stadiums/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody body: Stadium) =
        service.update(body.copy(stadiumId = id))

    @DeleteMapping("/api/stadiums/{id}")
    fun delete(@PathVariable("id") id: Int) = service.delete(id)

}

@RestController
class TeamController(private val service: TeamService) {

    @GetMapping("/api/teams")
    fun getAll() = service.getAll()

    @GetMapping("/api/teams/{id}")
    fun getOne(@PathVariable("id") id: Int) = service.getOne(id)

    @PostMapping("/api/teams/create")
    fun create(@RequestBody body: Team) = service.create(body)

    @PutMapping("/api/teams/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody body: Team) =
        service.update(body.copy(teamId = id))

    @DeleteMapping("/api/teams/{id}")
    fun delete(@PathVariable("id") id: Int) = service.delete(id)

}


@RestController
class PlayerController(private val service: PlayerService) {

    @GetMapping("/api/players")
    fun getAll() = service.getAll()

    @GetMapping("/api/players/{id}")
    fun getOne(@PathVariable("id") id: Int) = service.getOne(id)

    @PostMapping("/api/players/create")
    fun create(@RequestBody body: Player) = service.create(body)

    @PutMapping("/api/players/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody body: Player) =
        service.update(body.copy(playerId = id))

    @DeleteMapping("/api/players/{id}")
    fun delete(@PathVariable("id") id: Int) = service.delete(id)

}

@RestController
class UserStatsController(private val service: UserStatsService) {

    @GetMapping("/api/users")
    fun getAll() = service.getAll()

    @GetMapping("/api/users/table")
    fun getTable() = service.getTable()

    @GetMapping("/api/users/{username}")
    fun getUser(@PathVariable("username") username: String) = service.getUserStats(username)

    @GetMapping("/api/users/me")
    fun getMe(): UserStatsModel {
        val username = getRequestUserName()
        return service.getUserStats(username)
    }

}
