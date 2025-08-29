package de.torpedomirror.backend.persistence.fitbitauth

import de.torpedomirror.backend.persistence.base.ManuallyAssignedIdEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.ZonedDateTime

@Entity
class FitbitAuth(
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    val userId: String,

    @Column(name = "access_token", nullable = false, updatable = true)
    var accessToken: String,

    @Column(name = "expires_at", nullable = false, updatable = true)
    var expiresAt: ZonedDateTime,

    @Column(name = "refresh_token", nullable = false, updatable = true)
    var refreshToken: String,
) : ManuallyAssignedIdEntity<String>() {
    override fun getId(): String {
        return this.userId
    }
}