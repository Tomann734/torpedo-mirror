package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.fitbit")
data class FitbitProperties(
    val moduleName: String,
)

