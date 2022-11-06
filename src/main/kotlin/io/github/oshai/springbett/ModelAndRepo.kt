package io.github.oshai.springbett

import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository


interface StadiumRepository : CoroutineCrudRepository<Stadium, Int>

data class Stadium(@Id val id: Int? = null, val name: String, val shortName: String, val city: String, val capacity: Int)

interface TeamRepository : CoroutineCrudRepository<Team, Int>

data class Team(@Id val id: Int? = null, val name: String, val shortName: String)

interface PlayerRepository : CoroutineCrudRepository<Player, Int>

data class Player(@Id val id: Int? = null, val name: String, val shortName: String)

