package io.github.oshai.springbett

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

@SpringBootApplication
class SpringbettApplication {

    @Bean
    fun http(sr: StadiumRepository) = coRouter {
        GET("/stadiums") {
            ServerResponse.ok().bodyAndAwait(sr.findAll())
        }
        GET("/stadiums/{id}") {
            ServerResponse.ok().bodyValueAndAwait(
                sr.findById(it.pathVariable("id").toInt()) ?: throw Exception(
                    "could not find stadium ${
                        it.pathVariable(
                            "id"
                        )
                    }"
                )
            )
        }
        POST("/stadiums/create") {
            ServerResponse.ok().bodyValueAndAwait(sr.save(it.awaitBody()))
        }
        DELETE("/stadiums/{id}") {
            ServerResponse.ok().bodyValueAndAwait(sr.deleteById(it.pathVariable("id").toInt()))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringbettApplication>(*args)
}


interface StadiumRepository : CoroutineCrudRepository<Stadium, Int>

data class Stadium(@Id val id: Int? = null, val name: String, val city: String, val capacity: Int)
