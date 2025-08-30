package de.torpedomirror.backend.controller

import de.torpedomirror.backend.dto.CreateModuleDto
import de.torpedomirror.backend.service.ModuleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * controller is not used as modules are created automatically when starting up
 * for debugging and testing it stays here and can be activated by removing denying path in security config
 */
@RestController
@RequestMapping("/modules")
class ModuleController(
    private val moduleService: ModuleService
) {
    @PostMapping
    fun createModule(
        @RequestBody
        createModuleRequest: CreateModuleDto
    ): ResponseEntity<CreateModuleDto> {
        val obj = moduleService.createModule(createModuleRequest)
        return ResponseEntity(obj, HttpStatus.CREATED)
    }

    @DeleteMapping("/{moduleName}")
    fun deleteModules(
        @PathVariable
        moduleName: String
    ): ResponseEntity<Void> {
        moduleService.deleteModule(moduleName)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{moduleName}")
    fun getModule(
        @PathVariable
        moduleName: String
    ): ResponseEntity<CreateModuleDto> {
        val obj = moduleService.getModule(moduleName)
        return ResponseEntity(obj, HttpStatus.OK)
    }
}