package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty

data class HeartRateVariability(
    @param:JsonProperty("dailyRmssd")
    val rmssdDay: Double,

    @param:JsonProperty("deepRmssd")
    val rmssdDeep: Double,
)
