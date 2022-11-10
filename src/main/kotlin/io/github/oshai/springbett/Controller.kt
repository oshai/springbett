package io.github.oshai.springbett

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController



@RestController
class NopController() {
    @GetMapping("/api/generalbets/cansubmitbets/") fun canSubmitBets() = true
    @GetMapping("/api/generalbets/has-bet/{user}") fun hasBet() = false
    @GetMapping("/api/games") fun games() = listOf<Any>()
    @GetMapping("/api/users/table") fun users() = listOf<Any>()
}
@RestController
class StadiumController(val service: StadiumService) {
    companion object {
        private const val entityName = "stadiums"
    }

    @GetMapping("/api/$entityName")
    fun getAll() = service.getAll()

    @GetMapping("/api/$entityName/{id}")
    fun getOne(@RequestParam("id") id: Int) = service.getOne(id)

    @PostMapping("/api/$entityName/create")
    fun create(@RequestBody body: Stadium) = service.create(body)

    @PutMapping("/api/$entityName/{id}")
    fun update(@RequestParam("id") id: Int, @RequestBody body: Stadium) =
        service.update(body.copy(id = id))

    @DeleteMapping("/api/$entityName/{id}")
    fun delete(@RequestParam("id") id: Int) = service.delete(id)

}

@RestController
class TeamController(val service: TeamService) {
    companion object {
        private const val entityName = "teams"
    }

    @GetMapping("/api/$entityName")
    fun getAll() = service.getAll()

    @GetMapping("/api/$entityName/{id}")
    fun getOne(@RequestParam("id") id: Int) = service.getOne(id)

    @PostMapping("/api/$entityName/create")
    fun create(@RequestBody body: Team) = service.create(body)

    @PutMapping("/api/$entityName/{id}")
    fun update(@RequestParam("id") id: Int, @RequestBody body: Team) =
        service.update(body.copy(id = id))

    @DeleteMapping("/api/$entityName/{id}")
    fun delete(@RequestParam("id") id: Int) = service.delete(id)

}



@RestController
class PlayerController(val service: PlayerService) {
    companion object {
        private const val entityName = "players"
    }

    @GetMapping("/api/$entityName")
    fun getAll() = service.getAll()

    @GetMapping("/api/$entityName/{id}")
    fun getOne(@RequestParam("id") id: Int) = service.getOne(id)

    @PostMapping("/api/$entityName/create")
    fun create(@RequestBody body: Player) = service.create(body)

    @PutMapping("/api/$entityName/{id}")
    fun update(@RequestParam("id") id: Int, @RequestBody body: Player) =
        service.update(body.copy(id = id))

    @DeleteMapping("/api/$entityName/{id}")
    fun delete(@RequestParam("id") id: Int) = service.delete(id)

}
