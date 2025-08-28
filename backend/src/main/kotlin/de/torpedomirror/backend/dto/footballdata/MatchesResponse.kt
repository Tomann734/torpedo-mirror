package de.torpedomirror.backend.dto.footballdata

import com.fasterxml.jackson.annotation.JsonProperty

data class MatchesResponse(
    @param:JsonProperty("matches")
    val matches: Set<Match>
)
