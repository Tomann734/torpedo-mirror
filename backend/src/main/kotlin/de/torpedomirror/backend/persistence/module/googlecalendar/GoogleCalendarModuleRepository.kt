package de.torpedomirror.backend.persistence.module.googlecalendar

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime
import java.util.*

interface GoogleCalendarModuleRepository : CrudRepository<GoogleCalendarModule, UUID> {
    @Query("""
        SELECT c FROM GoogleCalendarModule c
        JOIN Submodule s ON c.uuid = s.uuid
        WHERE s.module.name = :moduleName
        AND c.recordTime < :now
        ORDER BY c.recordTime DESC
        Limit 1
    """)
    fun findLatestByModuleName(
        @Param("moduleName")
        moduleName: String,

        @Param("now")
        now: ZonedDateTime
    ): GoogleCalendarModule?
}