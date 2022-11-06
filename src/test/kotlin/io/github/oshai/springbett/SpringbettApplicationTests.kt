package io.github.oshai.springbett

import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SpringbettApplicationTests(@Autowired val sr: StadiumRepository) {

	@Test
	fun contextLoads() = runBlocking {
		sr.save(Stadium(name = "Al Bayt Stadium", capacity = 60000, city =  "Al Khor", shortName = "bayt"))
		val stadiums = sr.findAll()
		Assertions.assertEquals(1, stadiums.count())
		Assertions.assertNotNull(stadiums.last().id)
	}
}
