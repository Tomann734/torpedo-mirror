package de.torpedomirror.backend.dto.module

data class WeatherModuleDto(
    override val name: String,
    override val type: String,
    val isDay: Boolean,
    val currentTemperature: Double,
    val isPrecipitating: Boolean,
    val isCloudy: Boolean,
    val maxTemperature: Double,
    val minTemperature: Double,
) : SubmoduleDto
