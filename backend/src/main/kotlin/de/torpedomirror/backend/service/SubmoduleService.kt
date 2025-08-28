package de.torpedomirror.backend.service

import de.torpedomirror.backend.exception.ModuleNotFoundException
import de.torpedomirror.backend.external.FootballDataClient
import de.torpedomirror.backend.external.GoogleCalendarClient
import de.torpedomirror.backend.external.WeatherDataClient
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.ModuleRepository
import de.torpedomirror.backend.persistence.module.base.Submodule
import de.torpedomirror.backend.persistence.module.football.FootballModule
import de.torpedomirror.backend.persistence.module.football.FootballModuleRepository
import de.torpedomirror.backend.persistence.module.googlecalendar.GoogleCalendarModule
import de.torpedomirror.backend.persistence.module.googlecalendar.GoogleCalendarModuleRepository
import de.torpedomirror.backend.persistence.module.weather.WeatherModule
import de.torpedomirror.backend.persistence.module.weather.WeatherModuleRepository
import de.torpedomirror.backend.properties.FootballDataProperties
import de.torpedomirror.backend.properties.GoogleCalendarDataProperties
import de.torpedomirror.backend.properties.WeatherDataProperties
import de.torpedomirror.backend.util.toZonedDateTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class SubmoduleService(
    private val moduleRepository: ModuleRepository,
    private val footballModuleRepository: FootballModuleRepository,
    private val footballDataClient: FootballDataClient,
    private val footballDataProperties: FootballDataProperties,
    private val weatherModuleRepository: WeatherModuleRepository,
    private val weatherDataClient: WeatherDataClient,
    private val weatherDataProperties: WeatherDataProperties,
    private val googleCalendarModuleRepository: GoogleCalendarModuleRepository,
    private val googleCalendarClient: GoogleCalendarClient,
    private val googleCalendarDataProperties: GoogleCalendarDataProperties
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createFootballModule(now: ZonedDateTime) {
        val moduleName = footballDataProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val nextMatch = footballDataClient.getNextMatch(footballDataProperties.teamId)
        val homeTeamInformation = footballDataClient.getHomeTeamInformation(footballDataProperties.teamId)

        footballModuleRepository.save(
            FootballModule(
                module = module,
                recordTime = now,
                homeTeam = nextMatch.homeTeam.name,
                awayTeam = nextMatch.awayTeam.name,
                stadiumName = homeTeamInformation.venue,
                competition = nextMatch.competition.name,
                kickoffTime = nextMatch.utcDate.withZoneSameInstant(ZoneId.systemDefault()),
            )
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createWeatherModule(now: ZonedDateTime) {
        val moduleName = weatherDataProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val weather = weatherDataClient.getWeather(
            latitude = weatherDataProperties.latitude,
            longitude = weatherDataProperties.longitude,
        )

        weatherModuleRepository.save(
            WeatherModule(
                module = module,
                recordTime = ZonedDateTime.of(weather.current.time, ZoneId.systemDefault()) ?: now,
                isDay = weather.current.isDay == 1,
                currentTemperature = weather.current.temperature2m,
                currentRain = weather.current.rain,
                currentShower = weather.current.showers,
                currentSnow = weather.current.snowfall,
                currentCloudCover = weather.current.cloudCover,
                minTemperature = weather.hourly.temperature2m.min(),
                maxTemperature = weather.hourly.temperature2m.max(),
                minRain = weather.hourly.rain.min(),
                maxRain = weather.hourly.rain.max(),
                minShower = weather.hourly.showers.min(),
                maxShower = weather.hourly.showers.max(),
                minSnow = weather.hourly.snowfall.min(),
                maxSnow = weather.hourly.snowfall.max(),
                minCloudCoverage = weather.hourly.cloudCover.min(),
                maxCloudCoverage = weather.hourly.cloudCover.max(),
            )
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createGoogleCalendarModule(now: ZonedDateTime) {
        val moduleName = googleCalendarDataProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val nextEvent = googleCalendarClient.getNextEvent(
            now = now,
            calendarId = googleCalendarDataProperties.calendarId
        ) ?: run {
            logger.info("no next event for calendar ${googleCalendarDataProperties.calendarId}")
            return
        }

        googleCalendarModuleRepository.save(
            GoogleCalendarModule(
                module = module,
                recordTime = now,
                summary = nextEvent.summary,
                description = nextEvent.description,
                startTime = nextEvent.start.toZonedDateTime(),
                endTime = nextEvent.end.toZonedDateTime()
            )
        )
    }

    fun getLatestSubmoduleByModule(module: Module, now: ZonedDateTime): Submodule? {
        logger.error(ZoneId.systemDefault().id)
        return when (module.name) {
            footballDataProperties.moduleName -> {
                footballModuleRepository.findLatestByModuleName(
                    moduleName = module.name,
                    now = now
                )
            }
            weatherDataProperties.moduleName -> {
                weatherModuleRepository.findLatestByModuleName(
                    moduleName = module.name,
                    now = now
                )
            }
            googleCalendarDataProperties.moduleName -> {
                googleCalendarModuleRepository.findLatestByModuleName(
                    moduleName = module.name,
                    now = now
                )
            }
            else -> {
                throw ModuleNotFoundException(module.name)
            }
        }
    }

    private fun getUsedModuleByModuleName(moduleName: String): Module? {
        val module = moduleRepository.findByName(moduleName)
         ?: run {
             // error as module creation by initializer and each module should exist
            logger.error("module $moduleName not found")
            return null
        }

        if (module.users.isEmpty()) {
            logger.warn("module $moduleName is not used")
            return null
        }

        return module
    }
}