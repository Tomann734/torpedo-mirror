package de.torpedomirror.backend.persistence.module

import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.ModuleRepository
import de.torpedomirror.backend.persistence.module.fitbit.FitbitModule
import de.torpedomirror.backend.persistence.module.football.FootballModule
import de.torpedomirror.backend.persistence.module.googlecalendar.GoogleCalendarModule
import de.torpedomirror.backend.persistence.module.nasa.NasaModule
import de.torpedomirror.backend.persistence.module.weather.WeatherModule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ModuleInitializer(
    private val moduleRepository: ModuleRepository,

    @Value("\${torpedomirror.football.module-name}")
    private val footballModuleName: String,

    @Value("\${torpedomirror.weather.module-name}")
    private val weatherModuleName: String,

    @Value("\${torpedomirror.google-calendar.module-name}")
    private val googleCalendarModuleName: String,

    @Value("\${torpedomirror.fitbit.module-name}")
    private val fitbitModuleName: String,

    @Value("\${torpedomirror.nasa.module-name}")
    private val nasaModuleName: String,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    @EventListener(ApplicationReadyEvent::class)
    fun initializeModules() {
        createModuleIfNotExists(footballModuleName, FootballModule::class.simpleName!!)
        createModuleIfNotExists(weatherModuleName, WeatherModule::class.simpleName!!)
        createModuleIfNotExists(googleCalendarModuleName, GoogleCalendarModule::class.simpleName!!)
        createModuleIfNotExists(fitbitModuleName, FitbitModule::class.simpleName!!)
        createModuleIfNotExists(nasaModuleName, NasaModule::class.simpleName!!)
    }

    private fun createModuleIfNotExists(name: String, type: String) {
        if (!moduleRepository.existsById(name)) {
            moduleRepository.save(
                Module(
                    name = name,
                    type = type,
                )
            )
            logger.info("module $name created")
        }
    }
}
