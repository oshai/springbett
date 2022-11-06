package io.github.oshai.springbett

import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository


interface StadiumRepository : CoroutineCrudRepository<Stadium, Int>

data class Stadium(@Id val id: Int? = null, val name: String, val shortName: String, val city: String, val capacity: Int)




