package de.torpedomirror.backend.controller

import de.torpedomirror.backend.properties.NasaDataProperties
import de.torpedomirror.backend.properties.PersonalPictureProperties
import de.torpedomirror.backend.service.FileService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Path

@RestController
@RequestMapping("/file")
class FileController(
    private val fileService: FileService,
    private val nasaDataProperties: NasaDataProperties,
    private val personalPictureProperties: PersonalPictureProperties
) {
    @GetMapping("/nasa/{fileName}", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getNasaImage(
        @PathVariable
        fileName: String
    ): ResponseEntity<ByteArray> {
        val obj = fileService.readFile(
            directoryPath = Path.of(nasaDataProperties.directory),
            fileName = fileName
        )
        return ResponseEntity(obj, HttpStatus.OK)
    }

    @GetMapping("/personal-picture/{fileName}", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getPersonalPictureImage(
        @PathVariable
        fileName: String
    ): ResponseEntity<ByteArray> {
        val obj = fileService.readFile(
            directoryPath = Path.of(personalPictureProperties.directory),
            fileName = fileName
        )
        return ResponseEntity(obj, HttpStatus.OK)
    }
}