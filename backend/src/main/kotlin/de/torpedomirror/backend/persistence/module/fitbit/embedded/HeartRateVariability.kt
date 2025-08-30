package de.torpedomirror.backend.persistence.module.fitbit.embedded

import de.torpedomirror.backend.dto.fitbitdata.HeartRateVariability
import de.torpedomirror.backend.dto.module.fitbit.HeartRateVariabilityModuleDto
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class HeartRateVariability(
    @Column(name = "heart_rmssd_day", nullable = true, updatable = false)
    val rmssdDay: Double,

    @Column(name = "heart_rmssd_deep", nullable = true, updatable = false)
    val rmssdDeep: Double,
) {
    constructor(fetched: HeartRateVariability) : this(
        rmssdDay = fetched.rmssdDay,
        rmssdDeep = fetched.rmssdDeep
    )

    fun toDto(): HeartRateVariabilityModuleDto = HeartRateVariabilityModuleDto(
        rmssdDay = this.rmssdDay,
        rmssdDeep = this.rmssdDeep
    )
}