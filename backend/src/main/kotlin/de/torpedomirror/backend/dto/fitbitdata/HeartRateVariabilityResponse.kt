package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty

data class HeartRateVariabilityResponse(
    @param:JsonProperty("hrv")
    val heartRateVariabilities: Collection<HeartRateVariabilityDto>
)
