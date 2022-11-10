package io.github.oshai.springbett

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


fun main(args: Array<String>) {
	runApplication<SpringbettApplication>(*args)
}
@SpringBootApplication
class SpringbettApplication {

	// delete the db on startup
	@Bean
	fun cleanMigrateStrategy(): FlywayMigrationStrategy? {
		return FlywayMigrationStrategy { flyway ->
			flyway.clean()
			flyway.migrate()
		}
	}
}

