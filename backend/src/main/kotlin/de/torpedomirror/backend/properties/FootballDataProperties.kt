package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.football")
data class FootballDataProperties(
    val teamId: Int,
    val moduleName: String
)

