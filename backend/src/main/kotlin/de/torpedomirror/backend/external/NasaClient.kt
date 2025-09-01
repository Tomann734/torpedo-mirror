package de.torpedomirror.backend.external

import de.torpedomirror.backend.dto.nasa.PictureOfTheDayMeta
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI
import java.time.LocalDate

@Service
class NasaClient(
    private val nasaWebClient: WebClient
) {
    fun getPictureOfTheDayMeta(date: LocalDate): PictureOfTheDayMeta {
        return nasaWebClient
            .get()
            .uri {
                it.path("/planetary/apod")
                it.queryParam("api_key", "{api_key}")
                it.queryParam("date", date)
                it.build()
            }.retrieve()
            .bodyToMono(PictureOfTheDayMeta::class.java)
            .block()!!
    }

    fun downloadPictureOfTheDay(url: String): ByteArray {
        val imageBytes = nasaWebClient
            .get()
            .uri(URI(url))
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .block()!!

        return imageBytes
    }
}