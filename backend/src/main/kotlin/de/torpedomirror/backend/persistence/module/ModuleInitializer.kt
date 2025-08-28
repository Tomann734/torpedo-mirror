package de.torpedomirror.backend.persistence.module

import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.ModuleRepository
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
    private val googleCalendarModuleName: String
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    @EventListener(ApplicationReadyEvent::class)
    fun initializeModules() {
        createModuleIfNotExists(footballModuleName)
        createModuleIfNotExists(weatherModuleName)
        createModuleIfNotExists(googleCalendarModuleName)
    }

    private fun createModuleIfNotExists(name: String) {
        if (!moduleRepository.existsById(name)) {
            moduleRepository.save(Module(name = name))
            logger.info("module $name created")
        }
    }
}
