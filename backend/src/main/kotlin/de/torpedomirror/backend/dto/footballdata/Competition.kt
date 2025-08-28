package de.torpedomirror.backend.dto.footballdata

import com.fasterxml.jackson.annotation.JsonProperty

data class Competition(
    @param:JsonProperty("id")
    val id: Int,

    @param:JsonProperty("name")
    val name: String,

    @param:JsonProperty("code")
    val code: String,

    @param:JsonProperty("type")
    val type: String,
)
