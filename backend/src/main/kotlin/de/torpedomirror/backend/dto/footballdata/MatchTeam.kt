package de.torpedomirror.backend.dto.footballdata

import com.fasterxml.jackson.annotation.JsonProperty

data class MatchTeam(
    @param:JsonProperty("id")
    val id: Int,

    @param:JsonProperty("name")
    val name: String,

    @param:JsonProperty("shortName")
    val shortName: String,

    @param:JsonProperty("tla")
    val tla: String,

    @param:JsonProperty("crest")
    val crest: String
)

