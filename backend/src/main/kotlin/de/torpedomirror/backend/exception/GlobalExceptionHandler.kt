package de.torpedomirror.backend.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(MirrorUserAlreadyExistsException::class)
    fun handleMirrorUserAlreadyExists(ex: MirrorUserAlreadyExistsException): ResponseEntity<String> {
        logger.warn(ex.message)
        return ResponseEntity(ex.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(MirrorUserNotFoundException::class)
    fun handleMirrorUserNotFound(ex: MirrorUserNotFoundException): ResponseEntity<String> {
        logger.warn(ex.message)
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ModuleAlreadyExistsException::class)
    fun handleModuleAlreadyExists(ex: ModuleAlreadyExistsException): ResponseEntity<String> {
        logger.warn(ex.message)
        return ResponseEntity(ex.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ModuleNotFoundException::class)
    fun handleModuleNotFound(ex: ModuleNotFoundException): ResponseEntity<String> {
        logger.warn(ex.message)
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(FileNotFoundException::class)
    fun handleFileNotFound(ex: FileNotFoundException): ResponseEntity<String> {
        logger.warn(ex.message)
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
}