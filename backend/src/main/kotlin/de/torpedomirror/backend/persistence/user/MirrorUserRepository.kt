package de.torpedomirror.backend.persistence.user

import org.springframework.data.repository.CrudRepository

interface MirrorUserRepository : CrudRepository<MirrorUser, String> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): MirrorUser?
    fun deleteByUsername(username: String)
}