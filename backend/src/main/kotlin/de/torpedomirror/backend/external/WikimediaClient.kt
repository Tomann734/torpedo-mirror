package de.torpedomirror.backend.external

import de.torpedomirror.backend.dto.wikimediadata.WikimediaDataResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate

@Service
class WikimediaClient(
    private val wikimediaWebClient: WebClient,
) {
    fun getFactsOfTheDay(date: LocalDate): WikimediaDataResponse {
        val dateStr = "${date.monthValue}/${date.dayOfMonth}"

        return wikimediaWebClient.get()
            .uri(dateStr)
            .retrieve()
            .bodyToMono(WikimediaDataResponse::class.java)
            .block()!!
    }
}