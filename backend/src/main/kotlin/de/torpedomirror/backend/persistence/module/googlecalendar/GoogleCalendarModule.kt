package de.torpedomirror.backend.persistence.module.googlecalendar

import de.torpedomirror.backend.dto.module.GoogleCalendarModuleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.ZonedDateTime

@Entity
class GoogleCalendarModule(
    module: Module,

    recordTime: ZonedDateTime,

    @Column(name = "calendar_id", nullable = false, updatable = false)
    var calendarId: String,

    @Column(name = "summary", nullable = false, updatable = false)
    var summary: String,

    @Column(name = "description", nullable = true, updatable = false)
    var description: String?,

    @Column(name = "start_time", nullable = false, updatable = false)
    var startTime: ZonedDateTime,

    @Column(name = "end_time", nullable = false, updatable = false)
    var endTime: ZonedDateTime
) : Submodule(
    module = module,
    recordTime = recordTime
) {
    override fun toDto(): GoogleCalendarModuleDto = GoogleCalendarModuleDto(
        name = module.name,
        type = this::class.simpleName!!,
        calendarId = calendarId,
        summary = summary,
        description = description,
        startTime = startTime,
        endTime = endTime,
    )
}