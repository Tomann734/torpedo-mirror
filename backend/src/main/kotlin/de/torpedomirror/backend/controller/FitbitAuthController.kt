package de.torpedomirror.backend.controller

import de.torpedomirror.backend.service.FitbitAuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class FitbitAuthController(
    private val fitbitAuthService: FitbitAuthService
) {
    @GetMapping("/fitbitAuthCallback")
    fun fitbitAuthCallback(
        @RequestParam("code")
        code: String
    ) {
        fitbitAuthService.handleCallback(code, ZonedDateTime.now())
    }
}