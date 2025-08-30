package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty

data class HeartActivityDto(
    @param:JsonProperty("value")
    val value: HeartActivity
)
