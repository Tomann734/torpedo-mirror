package de.torpedomirror.backend.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ScheduledFitbitAuthService(
    private val fitbitAuthService: FitbitAuthService
) {
    @Scheduled(fixedDelayString = "\${torpedomirror.fitbit.scheduled-auth}")
    fun refreshToken() {
        fitbitAuthService.refreshToken(ZonedDateTime.now())
    }
}