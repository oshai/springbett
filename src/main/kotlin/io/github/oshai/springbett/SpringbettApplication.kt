package io.github.oshai.springbett

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

@SpringBootApplication
class SpringbettApplication {

    @Bean
    fun stadiums(sr: StadiumRepository) = coRouter {
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
        PUT("/stadiums/{id}") {
            ServerResponse.ok()
                .bodyValueAndAwait(sr.save(it.awaitBody<Stadium>().copy(id = it.pathVariable("id").toInt())))
        }
        DELETE("/stadiums/{id}") {
            ServerResponse.ok().bodyValueAndAwait(sr.deleteById(it.pathVariable("id").toInt()))
        }
    }
    @Bean
    fun teams(tr: TeamRepository) = coRouter {
        GET("/teams") {
            ServerResponse.ok().bodyAndAwait(tr.findAll())
        }
        GET("/teams/{id}") {
            ServerResponse.ok().bodyValueAndAwait(
                tr.findById(it.pathVariable("id").toInt()) ?: throw Exception(
                    "could not find team ${
                        it.pathVariable(
                            "id"
                        )
                    }"
                )
            )
        }
        POST("/teams/create") {
            ServerResponse.ok().bodyValueAndAwait(tr.save(it.awaitBody()))
        }
        PUT("/teams/{id}") {
            ServerResponse.ok()
                .bodyValueAndAwait(tr.save(it.awaitBody<Team>().copy(id = it.pathVariable("id").toInt())))
        }
        DELETE("/teams/{id}") {
            ServerResponse.ok().bodyValueAndAwait(tr.deleteById(it.pathVariable("id").toInt()))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringbettApplication>(*args)
}

