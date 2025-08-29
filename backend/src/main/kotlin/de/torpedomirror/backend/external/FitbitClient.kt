package de.torpedomirror.backend.external

import de.torpedomirror.backend.dto.fitbitauth.FitbitTokenResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.util.Base64

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
}