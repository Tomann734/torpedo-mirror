package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.football")
data class FootballDataProperties(
    val external: ExternalFootballProperties,
    val teamId: Int,
    val moduleName: String
)

data class ExternalFootballProperties(
    val apiUrl: String,
    val apiKey: String
)

