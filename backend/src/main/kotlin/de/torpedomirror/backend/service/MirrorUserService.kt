package de.torpedomirror.backend.service

import de.torpedomirror.backend.dto.CreateMirrorUserDto
import de.torpedomirror.backend.dto.module.ModulesDto
import de.torpedomirror.backend.exception.MirrorUserAlreadyExistsException
import de.torpedomirror.backend.exception.MirrorUserNotFoundException
import de.torpedomirror.backend.persistence.module.base.ModuleRepository
import de.torpedomirror.backend.persistence.user.MirrorUser
import de.torpedomirror.backend.persistence.user.MirrorUserModuleRepository
import de.torpedomirror.backend.persistence.user.MirrorUserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class MirrorUserService(
    private val mirrorUserRepository: MirrorUserRepository,
    private val mirrorUserModuleRepository: MirrorUserModuleRepository,
    private val moduleRepository: ModuleRepository,
    private val submoduleService: SubmoduleService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun createMirrorUser(createMirrorUserRequest: CreateMirrorUserDto): CreateMirrorUserDto {
        val username = createMirrorUserRequest.username
        if (mirrorUserRepository.existsByUsername(username)) {
            throw MirrorUserAlreadyExistsException(username)
        }

        logger.info("create user $username")

        val saved = mirrorUserRepository.save(
            MirrorUser(
                username = username,
            )
        )

        return saved.toCreatedDto()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun deleteMirrorUser(username: String) {
        logger.info("delete user $username")
        mirrorUserRepository.deleteByUsername(username)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun getMirrorUser(username: String): CreateMirrorUserDto {
        val mirrorUser = mirrorUserRepository.findByUsername(username)
            ?: throw MirrorUserNotFoundException(username)

        return mirrorUser.toCreatedDto()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun addModuleToUser(username: String, moduleName: String) {
        logger.info("add module $moduleName to user $username")
        if (!mirrorUserRepository.existsByUsername(username)) {
            throw MirrorUserNotFoundException(username)
        }
        if (!moduleRepository.existsByName(moduleName)) {
            throw MirrorUserNotFoundException(moduleName)
        }
        mirrorUserModuleRepository.addModuleToUser(
            username = username,
            moduleName = moduleName
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun removeModuleOfUser(username: String, moduleName: String) {
        logger.info("remove module $moduleName from user $username")
        if (!mirrorUserRepository.existsByUsername(username)) {
            throw MirrorUserNotFoundException(username)
        }
        if (!moduleRepository.existsByName(moduleName)) {
            throw MirrorUserNotFoundException(moduleName)
        }
        mirrorUserModuleRepository.removeModuleFromUser(
            username = username,
            moduleName = moduleName
        )
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    fun getModulesOfUser(username: String): ModulesDto {
        val mirrorUser = mirrorUserRepository.findByUsername(username)
            ?: throw MirrorUserNotFoundException(username)

        val modules = mirrorUser.modules

        if (modules.isEmpty()) {
            return ModulesDto(
                modules = listOf()
            )
        }

        val submodules = modules.map {
            submoduleService.getLatestSubmoduleByModule(it)
        }.map {
            it?.toDto()
        }

        return ModulesDto(submodules.filterNotNull())
    }
}