package de.torpedomirror.backend.service

import de.torpedomirror.backend.dto.CreateModuleDto
import de.torpedomirror.backend.exception.ModuleAlreadyExistsException
import de.torpedomirror.backend.exception.ModuleNotFoundException
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.ModuleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class ModuleService(
    private val moduleRepository: ModuleRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createModule(createModuleRequest: CreateModuleDto): CreateModuleDto {
        val moduleName = createModuleRequest.name
        if (moduleRepository.existsByName(moduleName)) {
            throw ModuleAlreadyExistsException(moduleName)
        }

        logger.info("create module $moduleName")

        val saved = moduleRepository.save(
            Module(createModuleRequest)
        )

        return saved.toCreatedDto()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun deleteModule(moduleName: String) {
        logger.info("delete module $moduleName")
        moduleRepository.deleteByName(moduleName)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun getModule(moduleName: String): CreateModuleDto {
        val module = moduleRepository.findByName(moduleName)
            ?: throw ModuleNotFoundException(moduleName)

        return module.toCreatedDto()
    }
}