package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.wikimedia")
data class WikimediaProperties(
    val factCount: Int,
    val moduleName: String
)
