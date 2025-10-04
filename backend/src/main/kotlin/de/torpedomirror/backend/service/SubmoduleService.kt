package de.torpedomirror.backend.service

import de.torpedomirror.backend.exception.ModuleNotFoundException
import de.torpedomirror.backend.external.FitbitClient
import de.torpedomirror.backend.external.FootballDataClient
import de.torpedomirror.backend.external.GoogleCalendarClient
import de.torpedomirror.backend.external.NasaClient
import de.torpedomirror.backend.external.WeatherDataClient
import de.torpedomirror.backend.external.WikimediaClient
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.ModuleRepository
import de.torpedomirror.backend.persistence.module.base.Submodule
import de.torpedomirror.backend.persistence.module.base.SubmoduleRepository
import de.torpedomirror.backend.persistence.module.fitbit.FitbitModule
import de.torpedomirror.backend.persistence.module.fitbit.embedded.BreathingRate
import de.torpedomirror.backend.persistence.module.fitbit.embedded.HeartActivity
import de.torpedomirror.backend.persistence.module.fitbit.embedded.HeartRateVariability
import de.torpedomirror.backend.persistence.module.fitbit.embedded.Sleep
import de.torpedomirror.backend.persistence.module.football.FootballModule
import de.torpedomirror.backend.persistence.module.googlecalendar.GoogleCalendarModule
import de.torpedomirror.backend.persistence.module.nasa.NasaModule
import de.torpedomirror.backend.persistence.module.personalpicture.PersonalPictureModule
import de.torpedomirror.backend.persistence.module.weather.WeatherModule
import de.torpedomirror.backend.persistence.module.wikimedia.WikimediaFact
import de.torpedomirror.backend.persistence.module.wikimedia.WikimediaModule
import de.torpedomirror.backend.properties.FitbitProperties
import de.torpedomirror.backend.properties.FootballProperties
import de.torpedomirror.backend.properties.GoogleCalendarProperties
import de.torpedomirror.backend.properties.NasaProperties
import de.torpedomirror.backend.properties.PersonalProperties
import de.torpedomirror.backend.properties.WeatherProperties
import de.torpedomirror.backend.properties.WikimediaProperties
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
    private val footballDataClient: FootballDataClient,
    private val footballProperties: FootballProperties,
    private val weatherDataClient: WeatherDataClient,
    private val weatherProperties: WeatherProperties,
    private val googleCalendarClient: GoogleCalendarClient,
    private val googleCalendarProperties: GoogleCalendarProperties,
    private val fitbitClient: FitbitClient,
    private val fitbitProperties: FitbitProperties,
    private val fitbitAuthService: FitbitAuthService,
    private val nasaClient: NasaClient,
    private val nasaProperties: NasaProperties,
    private val personalProperties: PersonalProperties,
    private val wikimediaClient: WikimediaClient,
    private val wikimediaProperties: WikimediaProperties
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createModule(moduleName: String, now: ZonedDateTime) {
        val module = moduleRepository.findByName(moduleName)
            ?: run {
                throw ModuleNotFoundException(moduleName)
            }

        when (module.type) {
            FootballModule::class.simpleName -> createFootballModule(now)
            WeatherModule::class.simpleName -> createWeatherModule(now)
            GoogleCalendarModule::class.simpleName -> createGoogleCalendarModule(now)
            FitbitModule::class.simpleName -> createFitbitModule(now)
            NasaModule::class.simpleName -> createNasaModule(now)
            PersonalPictureModule::class.simpleName -> createPersonalPictureModule(now)
            WikimediaModule::class.simpleName -> createWikimediaModule(now)
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createFootballModule(now: ZonedDateTime) {
        val moduleName = footballProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val teamId = footballProperties.teamId
        val nextMatch = footballDataClient.getNextMatch(teamId)
        val homeTeamInformation = footballDataClient.getHomeTeamInformation(nextMatch.homeTeam.id)

        submoduleRepository.save(
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
        val moduleName = weatherProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val weather = weatherDataClient.getWeather(
            latitude = weatherProperties.latitude,
            longitude = weatherProperties.longitude,
        )

        submoduleRepository.save(
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
        val moduleName = googleCalendarProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val calendarId = googleCalendarProperties.calendarId
        val nextEvent = googleCalendarClient.getNextEvent(
            now = now,
            calendarId = calendarId
        ) ?: run {
            logger.info("no next event for calendar $calendarId")
            return
        }

        submoduleRepository.save(
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
        val moduleName = fitbitProperties.moduleName
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

        submoduleRepository.save(
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
        val moduleName = nasaProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        val currentUtcDate = ZonedDateTime.now(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toLocalDate()

        val usedDate = currentUtcDate.minusDays(nasaProperties.minusDays)

        val apodMetaInformation = nasaClient.getPictureOfTheDayMeta(usedDate)
        val pictureBytes = nasaClient.downloadPictureOfTheDay(apodMetaInformation.url)

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        val fileName = "apod-${apodMetaInformation.date}.jpg"
        fileService.saveFile(
            directoryPath = Path.of(nasaProperties.directory),
            fileName =fileName,
            data = pictureBytes,
        )

        submoduleRepository.save(
            NasaModule(
                module = module,
                recordTime = now,
                title = apodMetaInformation.title,
                description = apodMetaInformation.description,
                date = currentUtcDate,
                url = apodMetaInformation.url,
                fileName = fileName,
            )
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createPersonalPictureModule(now: ZonedDateTime) {
        val moduleName = personalProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        val fileName = fileService.getRandomFileNameOfDirectory(
            directoryPath = Path.of(personalProperties.directory)
        )

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        submoduleRepository.save(
            PersonalPictureModule(
                module = module,
                recordTime = now,
                fileName = fileName,
            )
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createWikimediaModule(now: ZonedDateTime) {
        val moduleName = wikimediaProperties.moduleName
        val module = getUsedModuleByModuleName(moduleName)
            ?: return

        val currentDate = now.toLocalDate()
        val facts = wikimediaClient.getFactsOfTheDay(currentDate).facts

        val randomFacts = selectRandomElementsFromList(
            list = facts,
            numberOfSections = wikimediaProperties.factCount
        )

        logger.info("create submodule for module ${module.name} of users ${module.users.map { it.username }}")

        submoduleRepository.save(
            WikimediaModule(
                module = module,
                recordTime = now,
                facts = randomFacts.map { fact ->
                    WikimediaFact(
                        description = fact.text,
                        year = fact.year
                    )
                }.toMutableSet()
            )
        )
    }

    private fun <T> selectRandomElementsFromList(
        list: List<T>,
        numberOfSections: Int,
        elementsPerSection: Int = 1
    ): List<T> {
        val listSize = list.size
        val totalRequiredElements = numberOfSections * elementsPerSection

        if (numberOfSections <= 0 || elementsPerSection <= 0 || listSize < totalRequiredElements) {
            return list
        }

        val sectionSize = listSize / numberOfSections
        if (sectionSize < elementsPerSection) {
            return list
        }

        val result = mutableListOf<T>()

        for (i in 0 until numberOfSections) {
            val startIndex = i * sectionSize
            val endIndex = if (i == numberOfSections - 1) listSize else (i + 1) * sectionSize
            val currentSection = list.subList(startIndex, endIndex)
            result.addAll(currentSection.shuffled().take(elementsPerSection))
        }

        return result
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