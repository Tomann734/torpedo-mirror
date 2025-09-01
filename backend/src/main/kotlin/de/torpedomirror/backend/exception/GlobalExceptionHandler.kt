package de.torpedomirror.backend.exception

import de.torpedomirror.backend.dto.ErrorInformationDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(MirrorUserAlreadyExistsException::class)
    fun handleMirrorUserAlreadyExists(ex: MirrorUserAlreadyExistsException): ResponseEntity<ErrorInformationDto> {
        return handleException(ex)
    }

    @ExceptionHandler(MirrorUserNotFoundException::class)
    fun handleMirrorUserNotFound(ex: MirrorUserNotFoundException): ResponseEntity<ErrorInformationDto> {
        return handleException(ex)
    }

    @ExceptionHandler(ModuleAlreadyExistsException::class)
    fun handleModuleAlreadyExists(ex: ModuleAlreadyExistsException): ResponseEntity<ErrorInformationDto> {
        return handleException(ex)
    }

    @ExceptionHandler(ModuleNotFoundException::class)
    fun handleModuleNotFound(ex: ModuleNotFoundException): ResponseEntity<ErrorInformationDto> {
        return handleException(ex)
    }

    @ExceptionHandler(FileNotFoundException::class)
    fun handleFileNotFound(ex: FileNotFoundException): ResponseEntity<ErrorInformationDto> {
        return handleException(ex)
    }

    private fun handleException(ex: Exception): ResponseEntity<ErrorInformationDto> {
        val firstElement = ex.stackTrace.firstOrNull()
        logger.warn("exception: ${ex.message} in ${firstElement?.className}.${firstElement?.methodName}() line ${firstElement?.lineNumber}")
        val errorInformation = ErrorInformationDto(
            message = ex.message
        )
        return ResponseEntity(errorInformation, HttpStatus.NOT_FOUND)
    }
}