package de.torpedomirror.backend.dto.footballdata

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class Match(
    @param:JsonProperty("utcDate")
    val utcDate: ZonedDateTime,

    @param:JsonProperty("matchday")
    val matchday: Int,

    @param:JsonProperty("competition")
    val competition: Competition,

    @param:JsonProperty("homeTeam")
    val homeTeam: MatchTeam,

    @param:JsonProperty("awayTeam")
    val awayTeam: MatchTeam
)

