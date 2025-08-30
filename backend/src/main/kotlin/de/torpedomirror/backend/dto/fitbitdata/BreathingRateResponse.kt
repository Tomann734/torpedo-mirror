package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty

data class BreathingRateResponse(
    @param:JsonProperty("br")
    val breathingRates: Collection<BreathingRateDto>
)
