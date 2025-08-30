package de.torpedomirror.backend.persistence.module.fitbit

import org.springframework.data.repository.CrudRepository

interface FitbitAuthRepository : CrudRepository<FitbitAuth, String> {
    fun findFirstByOrderByExpiresAtDesc(): FitbitAuth?
    fun deleteByUserId(userId: String)
}
