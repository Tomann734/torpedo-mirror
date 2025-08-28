package de.torpedomirror.backend.persistence.module.weather

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime
import java.util.UUID

interface WeatherModuleRepository : CrudRepository<WeatherModule, UUID> {
    @Query("""
        SELECT w FROM WeatherModule w
        JOIN Submodule s ON w.uuid = s.uuid
        WHERE s.module.name = :moduleName
        AND w.recordTime < :now
        ORDER BY w.recordTime DESC
        Limit 1
    """)
    fun findLatestByModuleName(
        @Param("moduleName")
        moduleName: String,

        @Param("now")
        now: ZonedDateTime
    ): WeatherModule?
}
