package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.stock")
data class StockProperties(
    val moduleName: String,
    val symbols: List<String>,
    val interval: String,
)