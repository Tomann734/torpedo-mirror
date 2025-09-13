package de.torpedomirror.backend.dto.wikimediadata

import com.fasterxml.jackson.annotation.JsonProperty

data class WikimediaData(
    @param:JsonProperty("text")
    val text: String,

    @param:JsonProperty("year")
    val year: Int
)
