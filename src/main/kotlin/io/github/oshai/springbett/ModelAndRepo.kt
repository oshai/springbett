package io.github.oshai.springbett

import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.repository.CrudRepository

interface StadiumRepository : CrudRepository<Stadium, Int>

@Table("stadium")
data class Stadium(@Id var  id: Int? = null, val name: String, val shortName: String, val city: String, val capacity: Int)

interface TeamRepository : CrudRepository<Team, Int>

@Table("team")
data class Team(@Id val id: Int? = null, val name: String, val shortName: String)

interface PlayerRepository : CrudRepository<Player, Int>

@Table("player")
data class Player(@Id val id: Int? = null, val name: String, val shortName: String)

