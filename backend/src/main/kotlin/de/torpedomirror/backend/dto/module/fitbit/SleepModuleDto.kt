package de.torpedomirror.backend.dto.module.fitbit

import java.time.ZonedDateTime

data class SleepModuleDto(
    val minutesInBed: Int,
    val minutesAsleep: Int,
    val efficiency: Int,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime,
)
