package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.google-calendar")
data class GoogleCalendarProperties(
    val calendarId: String,
    val moduleName: String,
)
