package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.fitbit")
data class FitbitDataProperties(
    val external: ExternalFitbitProperties
)

data class ExternalFitbitProperties(
    val apiUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUrl: String,
)
