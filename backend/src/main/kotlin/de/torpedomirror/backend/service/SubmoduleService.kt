package de.torpedomirror.backend.service

import de.torpedomirror.backend.external.FitbitClient
import de.torpedomirror.backend.external.FootballDataClient
import de.torpedomirror.backend.external.GoogleCalendarClient
import de.torpedomirror.backend.external.NasaClient
import de.torpedomirror.backend.external.WeatherDataClient
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.ModuleRepository
import de.torpedomirror.backend.persistence.module.base.Submodule
import de.torpedomirror.backend.persistence.module.base.SubmoduleRepository
import de.torpedomirror.backend.persistence.module.fitbit.FitbitModule
import de.torpedomirror.backend.persistence.module.fitbit.FitbitModuleRepository
import de.torpedomirror.backend.persistence.module.fitbit.embedded.BreathingRate
import de.torpedomirror.backend.persistence.module.fitbit.embedded.HeartActivity
import de.torpedomirror.backend.persistence.module.fitbit.embedded.HeartRateVariability
import de.torpedomirror.backend.persistence.module.fitbit.embedded.Sleep
import de.torpedomirror.backend.persistence.module.football.FootballModule
import de.torpedomirror.backend.persistence.module.football.FootballModuleRepository
import de.torpedomirror.backend.persistence.module.googlecalendar.GoogleCalendarModule
import de.torpedomirror.backend.persistence.module.googlecalendar.GoogleCalendarModuleRepository
import de.torpedomirror.backend.persistence.module.nasa.NasaModule
import de.torpedomirror.backend.persistence.module.nasa.NasaModuleRepository
import de.torpedomirror.backend.persistence.module.personalpicture.PersonalPictureModule
import de.torpedomirror.backend.persistence.module.personalpicture.PersonalPictureModuleRepository
import de.torpedomirror.backend.persistence.module.weather.WeatherModule
import de.torpedomirror.backend.persistence.module.weather.WeatherModuleRepository
import de.torpedomirror.backend.properties.FitbitDataProperties
import de.torpedomirror.backend.properties.FootballDataProperties
import de.torpedomirror.backend.properties.GoogleCalendarDataProperties
import de.torpedomirror.backend.properties.NasaDataProperties
import de.torpedomirror.backend.properties.PersonalPictureProperties
import de.torpedomirror.backend.properties.WeatherDataProperties
import de.torpedomirror.backend.util.toZonedDateTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.nio.file.Path
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.ZoneOffset

@Service
class SubmoduleService(
    private val fileService: FileService,
    private val moduleRepository: ModuleRepository,
    private val submoduleRepository: SubmoduleRepository,
    private val footballModuleRepository: FootballModuleRepository,
    private val footballDataClient: FootballDataClient,
    private val footballDataProperties: FootballDataProperties,
    private val weatherModuleRepository: WeatherModuleRepository,
    private val weatherDataClient: WeatherDataClient,
    private val weatherDataProperties: WeatherDataProperties,
    private val googleCalendarModuleRepository: GoogleCalendarModuleRepository,
    private val googleCalendarClient: GoogleCalendarClient,
    private val googleCalendarDataProperties: GoogleCalendarDataProperties,
    private val fitbitModuleRepository: FitbitModuleRepository,
    private val fitbitClient: FitbitClient,
    private val fitbitDataProperties: FitbitDataProperties,
    private val fitbitAuthService: FitbitAuthService,
    private val nasaModuleRepository: NasaModuleRepository,
    private val nasaClient: NasaClient,
    private val nasaDataProperties: NasaDataProperties,
    private val personalPictureModuleRepository: PersonalPictureModuleRepository,
    private val personalPictureProperties: PersonalPictureProperties
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createFootballModule(now: ZonedDateTime) {
        val moduleName = footballDataProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val teamId = footballDataProperties.teamId
        val nextMatch = footballDataClient.getNextMatch(teamId)
        val homeTeamInformation = footballDataClient.getHomeTeamInformation(nextMatch.homeTeam.id)

        footballModuleRepository.save(
            FootballModule(
                module = module,
                recordTime = now,
                teamId = teamId,
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
                recordTime = now,
                latitude = weather.latitude,
                longitude = weather.longitude,
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

        val calendarId = googleCalendarDataProperties.calendarId
        val nextEvent = googleCalendarClient.getNextEvent(
            now = now,
            calendarId = calendarId
        ) ?: run {
            logger.info("no next event for calendar $calendarId")
            return
        }

        googleCalendarModuleRepository.save(
            GoogleCalendarModule(
                calendarId = calendarId,
                module = module,
                recordTime = now,
                summary = nextEvent.summary,
                description = nextEvent.description,
                startTime = nextEvent.start.toZonedDateTime(),
                endTime = nextEvent.end.toZonedDateTime()
            )
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createFitbitModule(now: ZonedDateTime) {
        val moduleName = fitbitDataProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        val currentAuth = fitbitAuthService.getCurrentAuth()
            ?: run {
                logger.warn("no current auth for module $moduleName")
                return
            }

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val currentLocalDate = now.toLocalDate()
        val fetchedSleep = fitbitClient.getSleep(
            accessToken = currentAuth.accessToken,
            date = currentLocalDate,
        )
        val fetchedBreathingRate = fitbitClient.getBreathingRate(
            accessToken = currentAuth.accessToken,
            date = currentLocalDate
        )
        val fetchedHeartActivity = fitbitClient.getHeartActivity(
            accessToken = currentAuth.accessToken,
            date = currentLocalDate,
        )
        val fetchedHeartRateVariability = fitbitClient.getHeartRateVariability(
            accessToken = currentAuth.accessToken,
            date = currentLocalDate,
        )

        fitbitModuleRepository.save(
            FitbitModule(
                module = module,
                recordTime = now,
                userId = currentAuth.userId,
                heartActivity = fetchedHeartActivity?.let { HeartActivity(it) },
                heartRateVariability = fetchedHeartRateVariability?.let { HeartRateVariability(it) },
                breathingRate = fetchedBreathingRate?.let { BreathingRate(it) },
                sleep = fetchedSleep?.let { Sleep(it) },
            )
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createNasaModule(now: ZonedDateTime) {
        val moduleName = nasaDataProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        val currentUtcDate = ZonedDateTime.now(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toLocalDate()
        val apodMetaInformation = nasaClient.getPictureOfTheDayMeta(currentUtcDate)
        val pictureBytes = nasaClient.downloadPictureOfTheDay(apodMetaInformation.url)

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val fileName = "apod-${apodMetaInformation.date}.jpg"
        fileService.saveFile(
            directoryPath = Path.of(nasaDataProperties.directory),
            fileName =fileName,
            data = pictureBytes,
        )

        nasaModuleRepository.save(
            NasaModule(
                module = module,
                recordTime = now,
                title = apodMetaInformation.title,
                description = apodMetaInformation.description,
                url = apodMetaInformation.url,
                fileName = fileName,
            )
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createPersonalPictureModule(now: ZonedDateTime) {
        val moduleName = personalPictureProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        val fileName = fileService.getRandomFileNameOfDirectory(
            directoryPath = Path.of(personalPictureProperties.directory)
        )

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        personalPictureModuleRepository.save(
            PersonalPictureModule(
                module = module,
                recordTime = now,
                fileName = fileName,
            )
        )
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.MANDATORY)
    fun getLatestSubmoduleByModule(module: Module): Submodule? {
        return submoduleRepository.findFirstByModuleNameOrderByRecordTimeDesc(
            moduleName = module.name,
        )
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