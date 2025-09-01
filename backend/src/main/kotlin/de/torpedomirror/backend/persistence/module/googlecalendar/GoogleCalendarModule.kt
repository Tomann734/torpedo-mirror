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
    val calendarId: String,

    @Column(name = "summary", nullable = false, updatable = false)
    val summary: String,

    @Column(name = "description", nullable = true, updatable = false)
    val description: String?,

    @Column(name = "start_time", nullable = false, updatable = false)
    val startTime: ZonedDateTime,

    @Column(name = "end_time", nullable = false, updatable = false)
    val endTime: ZonedDateTime
) : Submodule(
    module = module,
    recordTime = recordTime
) {
    override fun toDto(): GoogleCalendarModuleDto = GoogleCalendarModuleDto(
        name = this.module.name,
        type = this.module.type,
        recordTime = this.recordTime,
        calendarId = this.calendarId,
        summary = this.summary,
        description = this.description,
        startTime = this.startTime,
        endTime = this.endTime,
    )
}