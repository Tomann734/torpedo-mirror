package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.weather")
data class WeatherProperties(
    val latitude: Double,
    val longitude: Double,
    val moduleName: String
)