package de.torpedomirror.backend.dto.wikimediadata

import com.fasterxml.jackson.annotation.JsonProperty

data class WikimediaDataResponse(
    @param:JsonProperty("selected")
    val facts: List<WikimediaData>
)