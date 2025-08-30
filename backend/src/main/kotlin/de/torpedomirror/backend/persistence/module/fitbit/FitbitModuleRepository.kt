package de.torpedomirror.backend.persistence.module.fitbit

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime
import java.util.*

interface FitbitModuleRepository : CrudRepository<FitbitModule, UUID> {
    @Query("""
        SELECT f FROM FitbitModule f
        JOIN Submodule s ON f.uuid = s.uuid
        WHERE s.module.name = :moduleName
        AND f.recordTime < :now
        ORDER BY f.recordTime DESC
        Limit 1
    """)
    fun findLatestByModuleName(
        @Param("moduleName")
        moduleName: String,

        @Param("now")
        now: ZonedDateTime
    ): FitbitModule?
}