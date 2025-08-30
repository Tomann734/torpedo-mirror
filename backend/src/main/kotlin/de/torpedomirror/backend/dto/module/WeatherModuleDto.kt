package de.torpedomirror.backend.dto.module

data class WeatherModuleDto(
    override val name: String,
    override val type: String,
    val latitude: Double,
    val longitude: Double,
    val isDay: Boolean,
    val currentTemperature: Double,
    val isPrecipitating: Boolean,
    val isCloudy: Boolean,
    val maxTemperature: Double,
    val minTemperature: Double,
) : SubmoduleDto
