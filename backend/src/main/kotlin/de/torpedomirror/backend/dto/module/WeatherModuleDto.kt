package de.torpedomirror.backend.dto.module

import java.time.ZonedDateTime

data class WeatherModuleDto(
    override val name: String,
    override val type: String,
    override val recordTime: ZonedDateTime,
    val latitude: Double,
    val longitude: Double,
    val isDay: Boolean,
    val currentTemperature: Double,
    val isPrecipitating: Boolean,
    val isCloudy: Boolean,
    val maxTemperature: Double,
    val minTemperature: Double,
) : SubmoduleDto
