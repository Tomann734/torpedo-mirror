package de.torpedomirror.backend.dto.nasadata

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class PictureOfTheDayMeta(
    @param:JsonProperty("date")
    val date: LocalDate,

    @param:JsonProperty("title")
    val title: String,

    @param:JsonProperty("explanation")
    val description: String,

    @param:JsonProperty("url")
    val url: String,

    @param:JsonProperty("hdurl")
    val hdurl: String,
)
