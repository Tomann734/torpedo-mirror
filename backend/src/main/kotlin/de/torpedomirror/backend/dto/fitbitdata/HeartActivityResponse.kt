package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty

data class HeartActivityResponse(
    @param:JsonProperty("activities-heart")
    val heartRateActivities: Collection<HeartActivityDto>
)
