package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.external")
data class ExternalProperties(
    val fitbit: ExternalFitbitProperties,
    val football: ExternalFootballProperties,
    val googleCalendar: ExternalGoogleCalendarProperties,
    val nasa: ExternalNasaProperties,
    val wikimedia: ExternalWikimediaProperties,
    val stock: ExternalStockProperties
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

data class ExternalNasaProperties(
    val apiUrl: String,
    val apiKey: String,
)

data class ExternalWikimediaProperties(
    val apiUrl: String,
)

data class ExternalStockProperties(
    val apiUrl: String,
    val apiKey: String,
)