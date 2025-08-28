package de.torpedomirror.backend.dto.weatherdata

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class HourlyWeather(
    @param:JsonProperty("time")
    val time: List<LocalDateTime>,

    @param:JsonProperty("temperature_2m")
    val temperature2m: List<Double>,

    @param:JsonProperty("is_day")
    val isDay: List<Int>,

    @param:JsonProperty("rain")
    val rain: List<Double>,

    @param:JsonProperty("showers")
    val showers: List<Double>,

    @param:JsonProperty("snowfall")
    val snowfall: List<Double>,

    @param:JsonProperty("cloud_cover")
    val cloudCover: List<Int>
)

