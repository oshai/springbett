package io.github.oshai.springbett

import mu.KotlinLogging
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView


private val logger = KotlinLogging.logger {}

@RestController
class NopController() {
    @GetMapping("/api/generalbets/cansubmitbets/")
    fun canSubmitBets() = true

}

@RestController
class RedirectController {
    @GetMapping("/bets_center")
    fun redirect1() = RedirectView("/")
    @GetMapping("/games/**")
    fun redirect2() = RedirectView("/")
    @GetMapping("/teams/**")
    fun redirect3() = RedirectView("/")
    @GetMapping("/stadiums/**")
    fun redirect4() = RedirectView("/")
    @GetMapping("/users/**")
    fun redirect5() = RedirectView("/")
    @GetMapping("/manage")
    fun redirect6() = RedirectView("/")
    @GetMapping("/manage_users")
    fun redirect7() = RedirectView("/")
}

@RestController
class GameController(val service: DetailedGameService) {
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
}

@RestController
class GeneralBetController(val service: GeneralBetService) {

    @GetMapping("/api/generalbets/has-bet/{username}")
    fun hasBet(@PathVariable("username") username: String) = service.hasBet(username)
    ///api/generalbets/user/oshai
    @GetMapping("/api/generalbets/user/{username}")
    fun getBet(@PathVariable("username") username: String) = service.getBetForUser(username)
    @GetMapping("/api/generalbets")
    fun getAll() = service.getAll()

    @PostMapping("/api/generalbets")
    fun create(@RequestBody body: CreateGeneralBet) = service.create(body)

    @PutMapping("/api/generalbets/{generalBetId}")
    fun update(@PathVariable("generalBetId") generalBetId: Int,
               @RequestBody body: GeneralBet) = service.update(body.copy(generalBetId = generalBetId))
}

@RestController
class BetController(val service: BetService) {

    @GetMapping("/api/bets/user/{username}")
    fun getUserBets(@PathVariable("username") username: String) = service.getUserBets(username)

    @GetMapping("/api/games/{gameId}/mybet")
    fun getUserBetForGame(@PathVariable("gameId") gameId: Int) = service.getUserBetForGame(gameId)

    @PostMapping("/api/bets/")
    fun create(@RequestBody body: BetCreate) = service.create(body)

}

@RestController
class StadiumController(val service: StadiumService) {
    companion object {
        private const val entityName = "stadiums"
    }

    @GetMapping("/api/$entityName")
    fun getAll() = service.getAll()

    @GetMapping("/api/$entityName/{id}")
    fun getOne(@PathVariable("id") id: Int) = service.getOne(id)

    @PostMapping("/api/$entityName/create")
    fun create(@RequestBody body: Stadium) = service.create(body)

    @PutMapping("/api/$entityName/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody body: Stadium) =
        service.update(body.copy(stadiumId = id))

    @DeleteMapping("/api/$entityName/{id}")
    fun delete(@PathVariable("id") id: Int) = service.delete(id)

}

@RestController
class TeamController(val service: TeamService) {
    companion object {
        private const val entityName = "teams"
    }

    @GetMapping("/api/$entityName")
    fun getAll() = service.getAll()

    @GetMapping("/api/$entityName/{id}")
    fun getOne(@PathVariable("id") id: Int) = service.getOne(id)

    @PostMapping("/api/$entityName/create")
    fun create(@RequestBody body: Team) = service.create(body)

    @PutMapping("/api/$entityName/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody body: Team) =
        service.update(body.copy(teamId = id))

    @DeleteMapping("/api/$entityName/{id}")
    fun delete(@PathVariable("id") id: Int) = service.delete(id)

}


@RestController
class PlayerController(val service: PlayerService) {
    companion object {
        private const val entityName = "players"
    }

    @GetMapping("/api/$entityName")
    fun getAll() = service.getAll()

    @GetMapping("/api/$entityName/{id}")
    fun getOne(@PathVariable("id") id: Int) = service.getOne(id)

    @PostMapping("/api/$entityName/create")
    fun create(@RequestBody body: Player) = service.create(body)

    @PutMapping("/api/$entityName/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody body: Player) =
        service.update(body.copy(playerId = id))

    @DeleteMapping("/api/$entityName/{id}")
    fun delete(@PathVariable("id") id: Int) = service.delete(id)

}

@RestController
class UserStatsController(val service: UserStatsService) {

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
