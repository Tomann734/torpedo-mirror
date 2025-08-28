package de.torpedomirror.backend.dto.module

import java.time.ZonedDateTime

data class GoogleCalendarModuleDto(
    override val name: String,
    override val type: String,
    val summary: String,
    val description: String?,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime,
) : SubmoduleDto
