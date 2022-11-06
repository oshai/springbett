package io.github.oshai.springbett

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportRuntimeHints
import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyToFlow
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter
import java.time.Instant
import java.time.ZonedDateTime

@SpringBootApplication
class SpringbettApplication {

	@Bean
	fun http(sr: StadiumRepository) = coRouter {
		GET("/stadiums") {
			ServerResponse.ok().bodyAndAwait(sr.findAll())
		}
		POST("/stadiums/create") {
			ServerResponse.ok().bodyValueAndAwait(sr.save(it.awaitBody()))
		}
		DELETE("/stadiums/{id}/delete") {
			ServerResponse.ok().bodyValueAndAwait(sr.deleteById(it.pathVariable("id").toInt()))
		}
	}
}

fun main(args: Array<String>) {
	runApplication<SpringbettApplication>(*args)
}


interface StadiumRepository : CoroutineCrudRepository<Stadium, Int>

data class Stadium(@Id val id: Int? = null, val name: String, val city: String, val capacity: Int)
