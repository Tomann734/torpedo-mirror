package de.torpedomirror.backend.external

import de.torpedomirror.backend.dto.fitbitauth.FitbitTokenResponse
import de.torpedomirror.backend.dto.fitbitdata.*
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.util.*

@Service
class FitbitClient(
    private val fitbitWebClient: WebClient
) {
    fun getToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
    ): FitbitTokenResponse{
        val authHeader = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())
        return fitbitWebClient.post()
            .uri("/oauth2/token")
            .header(HttpHeaders.AUTHORIZATION, "Basic $authHeader")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromFormData("grant_type", "authorization_code")
                    .with("code", code)
                    .with("redirect_uri", redirectUrl)
            )
            .retrieve()
            .bodyToMono(FitbitTokenResponse::class.java)
            .block()!!
    }

    fun refreshToken(
        clientId: String,
        clientSecret: String,
        refreshToken: String,
    ): FitbitTokenResponse{
        val authHeader = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())
        return fitbitWebClient.post()
            .uri("/oauth2/token")
            .header(HttpHeaders.AUTHORIZATION, "Basic $authHeader")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromFormData("grant_type", "refresh_token")
                    .with("refresh_token", refreshToken)
            )
            .retrieve()
            .bodyToMono(FitbitTokenResponse::class.java)
            .block()!!
    }

    fun getHeartRateVariability(
        accessToken: String,
        date: LocalDate
    ): HeartRateVariability? {
        return fitbitWebClient.get()
            .uri("/1/user/-/hrv/date/$date.json")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono(HeartRateVariabilityResponse::class.java)
            .block()!!
            .heartRateVariabilities
            .firstOrNull()
            ?.value
    }

    fun getHeartActivity(
        accessToken: String,
        date: LocalDate
    ): HeartActivity? {
        return fitbitWebClient.get()
            .uri("/1/user/-/activities/heart/date/$date/1d.json")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono(HeartActivityResponse::class.java)
            .block()!!
            .heartRateActivities
            .firstOrNull()
            ?.value
    }

    fun getBreathingRate(
        accessToken: String,
        date: LocalDate
    ): BreathingRate? {
        return fitbitWebClient.get()
            .uri("/1/user/-/br/date/$date.json")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono(BreathingRateResponse::class.java)
            .block()!!
            .breathingRates
            .firstOrNull()
            ?.value
    }

    fun getSleep(
        accessToken: String,
        date: LocalDate
    ): Sleep? {
        return fitbitWebClient.get()
            .uri("/1.2/user/-/sleep/date/$date.json")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono(SleepResponse::class.java)
            .block()!!
            .sleeps
            .firstOrNull()
    }
}