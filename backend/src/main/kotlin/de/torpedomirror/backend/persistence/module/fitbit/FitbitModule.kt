package de.torpedomirror.backend.persistence.module.fitbit

import de.torpedomirror.backend.dto.module.FitbitModuleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import de.torpedomirror.backend.persistence.module.fitbit.embedded.BreathingRate
import de.torpedomirror.backend.persistence.module.fitbit.embedded.HeartActivity
import de.torpedomirror.backend.persistence.module.fitbit.embedded.HeartRateVariability
import de.torpedomirror.backend.persistence.module.fitbit.embedded.Sleep
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import java.time.ZonedDateTime

@Entity
class FitbitModule(
    module: Module,

    recordTime: ZonedDateTime,

    @Column(name = "user_id", nullable = false, updatable = false)
    val userId: String,

    @Embedded
    val heartActivity: HeartActivity?,

    @Embedded
    val heartRateVariability: HeartRateVariability?,

    @Embedded
    val breathingRate: BreathingRate?,

    @Embedded
    val sleep: Sleep?
    ) : Submodule(
    module = module,
    recordTime = recordTime
) {
    override fun toDto(): FitbitModuleDto = FitbitModuleDto(
        name = this.module.name,
        type = this.module.type,
        recordTime = this.recordTime,
        userId = this.userId,
        heartActivity = this.heartActivity?.toDto(),
        heartRateVariability = this.heartRateVariability?.toDto(),
        breathingRate = this.breathingRate?.toDto(),
        sleep = this.sleep?.toDto(),
    )
}