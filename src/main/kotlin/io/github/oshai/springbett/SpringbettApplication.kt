package io.github.oshai.springbett

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class SpringbettApplication {

//	@Bean
// delete the db on startup
//	fun cleanMigrateStrategy(): FlywayMigrationStrategy? {
//		return FlywayMigrationStrategy { flyway ->
//			flyway.clean()
//			flyway.migrate()
//		}
//	}
}

fun main(args: Array<String>) {
	runApplication<SpringbettApplication>(*args)
}
