package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty

data class BreathingRateDto(
    @param:JsonProperty("value")
    val value: BreathingRate
)
