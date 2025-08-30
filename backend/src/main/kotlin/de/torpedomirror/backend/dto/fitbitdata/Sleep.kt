package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class Sleep(
    @param:JsonProperty("duration")
    val durationMs: Int,

    @param:JsonProperty("efficiency")
    val efficiency: Int,

    @param:JsonProperty("startTime")
    val startTime: LocalDateTime,

    @param:JsonProperty("endTime")
    val endTime: LocalDateTime,

    @param:JsonProperty("timeInBed")
    val minutesInBed: Int,

    @param:JsonProperty("minutesAsleep")
    val minutesAsleep: Int,

    @param:JsonProperty("isMainSleep")
    val isMainSleep: Boolean,
)
