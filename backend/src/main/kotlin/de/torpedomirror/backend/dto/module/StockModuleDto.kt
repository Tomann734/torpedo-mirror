package de.torpedomirror.backend.dto.module

import de.torpedomirror.backend.dto.module.stock.StockQuoteDto
import java.time.ZonedDateTime

data class StockModuleDto(
    override val name: String,
    override val type: String,
    override val recordTime: ZonedDateTime,
    val quotes: List<StockQuoteDto>,
) : SubmoduleDto
