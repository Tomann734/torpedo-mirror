package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.nasa")
data class NasaDataProperties(
    val moduleName: String,
    val apodFolder: String
)
