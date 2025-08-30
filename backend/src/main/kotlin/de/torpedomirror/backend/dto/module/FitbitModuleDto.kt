package de.torpedomirror.backend.dto.module

import de.torpedomirror.backend.dto.module.fitbit.BreathingRateModuleDto
import de.torpedomirror.backend.dto.module.fitbit.HeartActivityModuleDto
import de.torpedomirror.backend.dto.module.fitbit.HeartRateVariabilityModuleDto
import de.torpedomirror.backend.dto.module.fitbit.SleepModuleDto

data class FitbitModuleDto(
    override val name: String,
    override val type: String,
    val userId: String,
    val breathingRate: BreathingRateModuleDto?,
    val heartActivity: HeartActivityModuleDto?,
    val heartRateVariability: HeartRateVariabilityModuleDto?,
    val sleep: SleepModuleDto?,
) : SubmoduleDto

