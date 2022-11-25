package io.github.oshai.springbett

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}
fun main(args: Array<String>) {
	runApplication<SpringbettApplication>(*args)
}

@SpringBootApplication
class SpringbettApplication {

	// delete the db on startup
	@Bean
	fun cleanMigrateStrategy(): FlywayMigrationStrategy {
		return FlywayMigrationStrategy { flyway ->
			flyway.clean()
			flyway.migrate()
		}
	}
}

@Component
class RunOnStartup(val us: UserService, val tournamentCreator: TournamentCreator) {

	@PostConstruct
	fun init() {
		tournamentCreator.create()
		logger.info { "users are:\n${us.getAll()}" }
	}

}
