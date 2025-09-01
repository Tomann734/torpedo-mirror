package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.fitbit")
data class FitbitDataProperties(
    val moduleName: String,
)

