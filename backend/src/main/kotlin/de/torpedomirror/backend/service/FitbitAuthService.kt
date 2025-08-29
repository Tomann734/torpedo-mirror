package de.torpedomirror.backend.service

import de.torpedomirror.backend.external.FitbitClient
import de.torpedomirror.backend.persistence.fitbitauth.FitbitAuth
import de.torpedomirror.backend.persistence.fitbitauth.FitbitAuthRepository
import de.torpedomirror.backend.properties.FitbitDataProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class FitbitAuthService(
    private val fitbitAuthRepository: FitbitAuthRepository,
    private val fitbitDataProperties: FitbitDataProperties,
    private val fitbitClient: FitbitClient
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    fun handleCallback(code: String, now: ZonedDateTime) {
        logger.info("handle fitbit auth callback with code ${code.take(10)}[...]")

        val tokenResponse = fitbitClient.getToken(
            code = code,
            clientId = fitbitDataProperties.external.clientId,
            clientSecret = fitbitDataProperties.external.clientSecret,
            redirectUrl = fitbitDataProperties.external.redirectUrl,
        )

        fitbitAuthRepository.save(
            FitbitAuth(
                userId = tokenResponse.userId,
                accessToken = tokenResponse.accessToken,
                expiresAt = now.plusSeconds(tokenResponse.expiresIn),
                refreshToken = tokenResponse.refreshToken,
            )
        )
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    fun refreshToken(now: ZonedDateTime) {
        val currentAuth = fitbitAuthRepository.findFirstByOrderByExpiresAtDesc()
        if (currentAuth == null) {
            logger.warn("no current auth for fitbit")
            return
        }

        // when token is valid long enough do nothing
        if (now.plusHours(1).isBefore(currentAuth.expiresAt)) {
            logger.debug("token for fitbit user ${currentAuth.userId} is still valid until ${currentAuth.expiresAt}")
            return
        }

        logger.info("refreshing token for fitbit user ${currentAuth.userId}")

        val tokenResponse = fitbitClient.refreshToken(
            refreshToken = currentAuth.refreshToken,
            clientId = fitbitDataProperties.external.clientId,
            clientSecret = fitbitDataProperties.external.clientSecret,
        )

        currentAuth.accessToken = tokenResponse.accessToken
        currentAuth.expiresAt = now.plusSeconds(tokenResponse.expiresIn)
        currentAuth.refreshToken = tokenResponse.refreshToken
        fitbitAuthRepository.save(currentAuth)
    }
}