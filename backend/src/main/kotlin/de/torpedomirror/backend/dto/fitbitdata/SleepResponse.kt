package de.torpedomirror.backend.dto.fitbitdata

import com.fasterxml.jackson.annotation.JsonProperty

data class SleepResponse(
    @param:JsonProperty("sleep")
    val sleeps: Collection<Sleep>
)
