package de.torpedomirror.backend.persistence.module.fitbit.embedded

import de.torpedomirror.backend.dto.fitbitdata.HeartActivity
import de.torpedomirror.backend.dto.module.fitbit.HeartActivityModuleDto
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class HeartActivity(
    @Column(name = "heart_resting_rate", nullable = true, updatable = false)
    val heartRestingRate: Int,
) {
    constructor(fetched: HeartActivity) : this (
        heartRestingRate = fetched.restingHeartRate,
    )

    fun toDto(): HeartActivityModuleDto = HeartActivityModuleDto(
        heartRestingRate = this.heartRestingRate
    )
}