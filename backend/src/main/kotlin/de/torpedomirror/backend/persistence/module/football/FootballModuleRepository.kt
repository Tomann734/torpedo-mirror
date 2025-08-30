package de.torpedomirror.backend.persistence.module.football

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime
import java.util.*

interface FootballModuleRepository : CrudRepository<FootballModule, UUID> {
    @Query("""
        SELECT f FROM FootballModule f
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
    ): FootballModule?
}