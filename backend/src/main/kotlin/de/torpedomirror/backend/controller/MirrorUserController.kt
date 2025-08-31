package de.torpedomirror.backend.controller

import de.torpedomirror.backend.dto.CreateMirrorUserDto
import de.torpedomirror.backend.dto.ModuleNameDto
import de.torpedomirror.backend.dto.module.ModulesDto
import de.torpedomirror.backend.service.MirrorUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.ZonedDateTime

@RestController
@RequestMapping("/users")
class MirrorUserController(
    private val mirrorUserService: MirrorUserService
) {
    @PostMapping
    fun createMirrorUser(
        @RequestBody
        createMirrorUserRequest: CreateMirrorUserDto
    ): ResponseEntity<CreateMirrorUserDto> {
        val obj = mirrorUserService.createMirrorUser(createMirrorUserRequest)
        return ResponseEntity(obj, HttpStatus.CREATED)
    }

    @DeleteMapping("/{username}")
    fun deleteMirrorUser(
        @PathVariable
        username: String
    ): ResponseEntity<Void> {
        mirrorUserService.deleteMirrorUser(username)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{username}")
    fun getMirrorUser(
        @PathVariable
        username: String
    ): ResponseEntity<CreateMirrorUserDto> {
        val obj = mirrorUserService.getMirrorUser(username)
        return ResponseEntity(obj, HttpStatus.OK)
    }

    @PostMapping("/{username}/module")
    fun addModuleToUser(
        @PathVariable
        username: String,

        @RequestBody
        addModuleRequest: ModuleNameDto
    ): ResponseEntity<Void> {
        mirrorUserService.addModuleToUser(
            username = username,
            moduleName = addModuleRequest.moduleName
        )
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("/{username}/module")
    fun removeModuleFromUser(
        @PathVariable
        username: String,

        @RequestBody
        removeModuleRequest: ModuleNameDto
    ): ResponseEntity<Void> {
        mirrorUserService.removeModuleOfUser(
            username = username,
            moduleName = removeModuleRequest.moduleName
        )
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{username}/module")
    fun getModulesOfUser(
        @PathVariable
        username: String,
    ): ResponseEntity<ModulesDto> {
        val obj = mirrorUserService.getModulesOfUser(
            username = username,
        )
        return ResponseEntity(obj, HttpStatus.OK)
    }
}