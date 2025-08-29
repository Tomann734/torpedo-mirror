package de.torpedomirror.backend.external

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.ServiceAccountCredentials
import de.torpedomirror.backend.properties.FitbitDataProperties
import de.torpedomirror.backend.properties.FootballDataProperties
import de.torpedomirror.backend.properties.WeatherDataProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ExternalConfiguration {
    @Bean
    fun footballDataWebClient(properties: FootballDataProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.external.apiUrl)
            .defaultHeader("X-Auth-Token", properties.external.apiKey)
            .build()
    }

    @Bean
    fun weatherDataWebClient(properties: WeatherDataProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.external.apiUrl)
            .build()
    }

    @Bean
    fun googleCalendar(): Calendar {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory = GsonFactory.getDefaultInstance()

        val serviceAccountStream = ClassPathResource("/credentials/service-account.json").inputStream
        val credentials = ServiceAccountCredentials.fromStream(serviceAccountStream)
            .createScoped(listOf(CalendarScopes.CALENDAR_READONLY))

        val requestInitializer = HttpCredentialsAdapter(credentials)

        return Calendar.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("TorpedoCalendar")
            .build()
    }

    @Bean
    fun fitbitWebClient(properties: FitbitDataProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.external.apiUrl)
            .build()
    }
}