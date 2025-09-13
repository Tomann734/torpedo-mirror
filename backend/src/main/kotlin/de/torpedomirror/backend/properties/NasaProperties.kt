package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.nasa")
data class NasaProperties(
    val moduleName: String,
    val directory: String
)
