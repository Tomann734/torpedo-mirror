package de.torpedomirror.backend.persistence.module.stock

import de.torpedomirror.backend.dto.module.stock.StockQuoteDto
import de.torpedomirror.backend.persistence.module.stock.embedded.QuoteIdClass
import io.finnhub.api.models.Quote
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "stock_quote")
class QuoteData(
    @EmbeddedId
    val quoteIdClass: QuoteIdClass,

    @Column(name = "symbol_name", nullable = false, updatable = false)
    val symbolName: String,

    @Column(name = "current_price", nullable = false, updatable = false)
    val currentPrice: Float,

    @Column(name = "change", nullable = false, updatable = false)
    val change: Float,

    @Column(name = "change_percent", nullable = false, updatable = false)
    val changePercent: Float,

    @Column(name = "day_highest_price", nullable = false, updatable = false)
    val dayHighestPrice: Float,

    @Column(name = "day_lowest_price", nullable = false, updatable = false)
    val dayLowestPrice: Float,

    @Column(name = "day_open_price", nullable = false, updatable = false)
    val dayOpenPrice: Float,

    @Column(name = "previous_close_price", nullable = false, updatable = false)
    val previousClosePrice: Float,
) {
    constructor(recordTime: ZonedDateTime, symbol: String, symbolName: String, quoteDto: Quote) : this(
        quoteIdClass = QuoteIdClass(
            symbol = symbol,
            recordTime = recordTime
        ),
        symbolName = symbolName,
        currentPrice = quoteDto.c ?: 0f,
        change = quoteDto.d ?: 0f,
        changePercent = quoteDto.dp ?: 0f,
        dayHighestPrice = quoteDto.h ?: 0f,
        dayLowestPrice = quoteDto.l ?: 0f,
        dayOpenPrice = quoteDto.o ?: 0f,
        previousClosePrice = quoteDto.pc ?: 0f
    )

    fun toDto() = StockQuoteDto(
        symbol = symbolName,
        currentPrice = currentPrice,
        change = change,
        changePercent = changePercent,
        dayHighestPrice = dayHighestPrice,
        dayLowestPrice = dayLowestPrice,
        dayOpenPrice = dayOpenPrice,
        previousClosePrice = previousClosePrice,
    )
}