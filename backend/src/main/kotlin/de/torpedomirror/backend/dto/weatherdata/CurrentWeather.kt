package de.torpedomirror.backend.dto.weatherdata

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class CurrentWeather(
    @param:JsonProperty("time")
    val time: LocalDateTime?,          // ISO8601

    @param:JsonProperty("interval")
    val interval: Int,         // z.B. 900

    @param:JsonProperty("temperature_2m")
    val temperature2m: Double, // °C

    @param:JsonProperty("is_day")
    val isDay: Int,            // 1 = day, 0 = night

    @param:JsonProperty("rain")
    val rain: Double,          // mm

    @param:JsonProperty("showers")
    val showers: Double,       // mm

    @param:JsonProperty("snowfall")
    val snowfall: Double,      // cm

    @param:JsonProperty("cloud_cover")
    val cloudCover: Int        // %
)

