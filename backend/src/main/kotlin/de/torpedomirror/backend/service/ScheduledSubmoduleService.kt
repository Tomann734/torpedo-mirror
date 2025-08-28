package de.torpedomirror.backend.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ScheduledSubmoduleService(
    private val submoduleService: SubmoduleService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelayString = "\${torpedomirror.football.scheduled-data}")
    fun createFootballSubmodule() {
        submoduleService.createFootballModule(ZonedDateTime.now())
    }

    @Scheduled(fixedDelayString = "\${torpedomirror.weather.scheduled-data}")
    fun createWeatherSubmodule() {
        submoduleService.createWeatherModule(ZonedDateTime.now())
    }

    @Scheduled(fixedDelayString = "\${torpedomirror.google-calendar.scheduled-data}")
    fun createGoogleCalendarSubmodule() {
        submoduleService.createGoogleCalendarModule(ZonedDateTime.now())
    }
}