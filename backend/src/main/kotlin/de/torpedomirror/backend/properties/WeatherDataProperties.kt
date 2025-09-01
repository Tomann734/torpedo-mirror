package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.weather")
data class WeatherDataProperties(
    val latitude: Double,
    val longitude: Double,
    val moduleName: String
)