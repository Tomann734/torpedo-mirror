package de.torpedomirror.backend.external

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.ServiceAccountCredentials
import de.torpedomirror.backend.properties.ExternalProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.io.FileInputStream

@Configuration
class ExternalConfiguration {
    @Bean
    fun footballDataWebClient(properties: ExternalProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.football.apiUrl)
            .defaultHeader("X-Auth-Token", properties.football.apiKey)
            .build()
    }

    @Bean
    fun weatherDataWebClient(properties: ExternalProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.weather.apiUrl)
            .build()
    }

    @Bean
    fun googleCalendar(properties: ExternalProperties): Calendar {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory = GsonFactory.getDefaultInstance()

        val serviceAccountStream = FileInputStream(properties.googleCalendar.credentialsPath)
        val credentials = ServiceAccountCredentials.fromStream(serviceAccountStream)
            .createScoped(listOf(CalendarScopes.CALENDAR_READONLY))

        val requestInitializer = HttpCredentialsAdapter(credentials)

        return Calendar.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("TorpedoCalendar")
            .build()
    }

    @Bean
    fun fitbitWebClient(properties: ExternalProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.fitbit.apiUrl)
            .build()
    }

    @Bean
    fun nasaWebClient(properties: ExternalProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.nasa.apiUrl)
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)
            }
            .defaultUriVariables(mapOf("api_key" to properties.nasa.apiKey))
            .build()
    }

    @Bean
    fun wikimediaWebClient(properties: ExternalProperties, builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(properties.wikimedia.apiUrl)
            .build()
    }
}