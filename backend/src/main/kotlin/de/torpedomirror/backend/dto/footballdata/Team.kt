package de.torpedomirror.backend.dto.footballdata

import com.fasterxml.jackson.annotation.JsonProperty

data class Team(
    @param:JsonProperty("id")
    val id: Int,

    @param:JsonProperty("name")
    val name: String,

    @param:JsonProperty("shortName")
    val shortName: String,

    @param:JsonProperty("tla")
    val tla: String,

    @param:JsonProperty("address")
    val address: String,

    @param:JsonProperty("founded")
    val founded: Int,

    @param:JsonProperty("clubColors")
    val clubColors: String,

    @param:JsonProperty("venue")
    val venue: String,

    @param:JsonProperty("crest")
    val crest: String
)


