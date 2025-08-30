package de.torpedomirror.backend.persistence.module.fitbit.embedded

import de.torpedomirror.backend.dto.fitbitdata.BreathingRate
import de.torpedomirror.backend.dto.module.fitbit.BreathingRateModuleDto
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class BreathingRate(
    @Column(name = "breathing_rate", nullable = true, updatable = false)
    val breathingRate: Double,
) {
    constructor(fetched: BreathingRate) : this(
        breathingRate = fetched.breathingRate,
    )

    fun toDto(): BreathingRateModuleDto = BreathingRateModuleDto(
        breathingRate = this.breathingRate
    )
}