package io.github.oshai.springbett

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class StadiumService(val repository: StadiumRepository) {
    fun getOne(id: Int): Stadium {
        return repository.findById(id).orElseThrow { Exception("stadium $id not found") }
    }

    fun getAll(): List<Stadium> {
        return repository.findAll().toList()
    }

    fun create(obj: Stadium): Stadium {
        return repository.save(obj.copy(id = null))
    }

    fun update(obj: Stadium): Stadium {
        if (!repository.existsById(obj.id!!)) {
            throw Exception("stadium ${obj.id} do not exists")
        }
        return repository.save(obj.copy(id = null))
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}


@Service
class TeamService(val repository: TeamRepository) {
    fun getOne(id: Int): Team {
        return repository.findById(id).orElseThrow { Exception("team $id not found") }
    }

    fun getAll(): List<Team> {
        return repository.findAll().toList()
    }

    fun create(obj: Team): Team {
        return repository.save(obj.copy(id = null))
    }

    fun update(obj: Team): Team {
        if (!repository.existsById(obj.id!!)) {
            throw Exception("team ${obj.id} do not exists")
        }
        return repository.save(obj.copy(id = null))
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}

@Service
class PlayerService(val repository: PlayerRepository) {
    fun getOne(id: Int): Player {
        return repository.findById(id).orElseThrow { Exception("player $id not found") }
    }

    fun getAll(): List<Player> {
        return repository.findAll().toList()
    }

    fun create(obj: Player): Player {
        return repository.save(obj.copy(id = null))
    }

    fun update(obj: Player): Player {
        if (!repository.existsById(obj.id!!)) {
            throw Exception("player ${obj.id} do not exists")
        }
        return repository.save(obj.copy(id = null))
    }

    fun delete(id: Int) {
        repository.deleteById(id)
    }
}

@Service
class UserService(val repository: UserRepository) {
    fun getOne(id: UUID): User {
        return repository.findById(id).orElseThrow { Exception("user $id not found") }
    }

    fun getOne(username: String): User {
        return getAll().firstOrNull { it.username == username } ?: throw Exception("iser $username not found")
    }

    fun getAll(): List<User> {
        return repository.findAll().toList()
    }

    fun create(obj: User): User {
        return repository.save(obj.copy(id = null))
    }

    fun update(obj: User): User {
        if (!repository.existsById(obj.id!!)) {
            throw Exception("player ${obj.id} do not exists")
        }
        return repository.save(obj.copy(id = null))
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }
}