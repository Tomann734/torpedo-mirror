package de.torpedomirror.backend.external

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class GoogleCalendarClient(
    private val googleCalendar: Calendar
) {
    fun getNextEvent(now: ZonedDateTime, calendarId: String): Event? {
        val events = googleCalendar
            .events()
            .list(calendarId)
            .setMaxResults(1)
            .setTimeMin(DateTime(now.toInstant().toEpochMilli()))
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()

        return events.items.firstOrNull()
    }
}