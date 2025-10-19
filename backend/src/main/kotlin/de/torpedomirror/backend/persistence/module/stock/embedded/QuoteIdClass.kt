package de.torpedomirror.backend.persistence.module.stock.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.ZonedDateTime

@Embeddable
data class QuoteIdClass(
    @Column(name = "symbol", nullable = false, updatable = false)
    val symbol: String,

    @Column(name = "record_time", nullable = false, updatable = false)
    val recordTime: ZonedDateTime,
)
