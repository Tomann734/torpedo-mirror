package de.torpedomirror.backend.external

import io.finnhub.api.models.SymbolLookup
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class StockClient(
    private val alphavantageWebClient: WebClient
) {
    fun getStockData(symbol: String): StockDataDto {
        return finnhubApiClient.symbolSearch(symbol)
    }
}