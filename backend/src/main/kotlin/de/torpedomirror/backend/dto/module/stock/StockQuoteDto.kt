package de.torpedomirror.backend.dto.module.stock

data class StockQuoteDto(
    val symbol: String,
    val currentPrice: Float,
    val change: Float,
    val changePercent: Float,
    val dayHighestPrice: Float,
    val dayLowestPrice: Float,
    val dayOpenPrice: Float,
    val previousClosePrice: Float,
)
