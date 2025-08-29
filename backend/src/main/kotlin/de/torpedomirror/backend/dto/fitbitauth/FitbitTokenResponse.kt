package de.torpedomirror.backend.dto.fitbitauth

import com.fasterxml.jackson.annotation.JsonProperty

data class FitbitTokenResponse(
    @param:JsonProperty("access_token")
    val accessToken: String,

    @param:JsonProperty("refresh_token")
    val refreshToken: String,

    @param:JsonProperty("expires_in")
    val expiresIn: Long,

    @param:JsonProperty("scope")
    val scope: String,

    @param:JsonProperty("token_type")
    val tokenType: String,

    @param:JsonProperty("user_id")
    val userId: String,
)