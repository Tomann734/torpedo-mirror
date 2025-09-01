package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.external")
data class ExternalProperties(
    val fitbit: ExternalFitbitProperties,
    val football: ExternalFootballProperties,
    val googleCalendar: ExternalGoogleCalendarProperties,
    val weather: ExternalWeatherProperties,
    val nasa: ExternalNasaProperties,
)

data class ExternalFitbitProperties(
    val apiUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUrl: String,
)

data class ExternalFootballProperties(
    val apiUrl: String,
    val apiKey: String
)

data class ExternalGoogleCalendarProperties(
    val credentialsPath: String,
)

data class ExternalWeatherProperties(
    val apiUrl: String,
)

data class ExternalNasaProperties(
    val apiUrl: String,
    val apiKey: String,
)