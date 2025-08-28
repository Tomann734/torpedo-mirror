package de.torpedomirror.backend.util

import com.google.api.services.calendar.model.EventDateTime
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun EventDateTime.toZonedDateTime(): ZonedDateTime {
    return when {
        this.dateTime != null -> {
            Instant.ofEpochMilli(this.dateTime.value).atZone(ZoneId.systemDefault())
        }
        this.date != null -> {
            Instant.ofEpochMilli(this.date.value).atZone(ZoneId.systemDefault())
        }
        else -> throw IllegalArgumentException("event date is in wrong format")
    }
}


