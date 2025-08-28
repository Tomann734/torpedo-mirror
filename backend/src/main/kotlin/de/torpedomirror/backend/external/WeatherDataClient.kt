package de.torpedomirror.backend.external

import de.torpedomirror.backend.dto.weatherdata.Weather
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.ZoneId

@Service
class WeatherDataClient(
    private val weatherDataWebClient: WebClient,
) {
    fun getWeather(
        latitude: Double,
        longitude: Double,
    ): Weather {
        val res = weatherDataWebClient
            .get()
            .uri {
                it.path("forecast")
                it.queryParam("latitude", latitude)
                it.queryParam("longitude", longitude)
                it.queryParam("timezone", ZoneId.systemDefault().id)
                it.queryParam("hourly", "temperature_2m,is_day,rain,showers,snowfall,cloud_cover")
                it.queryParam("current", "temperature_2m,is_day,rain,showers,snowfall,cloud_cover")
                it.queryParam("forecast_days", 1)
                it.build()
            }
            .retrieve()
            .bodyToMono(Weather::class.java)
            .block()!!

        return res
    }
}