package de.torpedomirror.backend.dto.weatherdata

import com.fasterxml.jackson.annotation.JsonProperty

data class Weather(
    @param:JsonProperty("latitude")
    val latitude: Double,

    @param:JsonProperty("longitude")
    val longitude: Double,

    @param:JsonProperty("timezone")
    val timezone: String,

    @param:JsonProperty("current")
    val current: CurrentWeather,

    @param:JsonProperty("hourly")
    val hourly: HourlyWeather
)

