package de.torpedomirror.backend.persistence.module.fitbit.embedded

import de.torpedomirror.backend.dto.fitbitdata.Sleep
import de.torpedomirror.backend.dto.module.fitbit.SleepModuleDto
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.ZoneId
import java.time.ZonedDateTime

@Embeddable
class Sleep(
    @Column(name = "sleep_minutes_in_bed", nullable = true, updatable = false)
    val minutesInBed: Int,

    @Column(name = "sleep_minutes_asleep", nullable = true, updatable = false)
    val minutesAsleep: Int,

    @Column(name = "sleep_efficiency", nullable = true, updatable = false)
    val efficiency: Int,

    @Column(name = "sleep_start_time", nullable = true, updatable = false)
    val startTime: ZonedDateTime,

    @Column(name = "sleep_end_time", nullable = true, updatable = false)
    val endTime: ZonedDateTime,
) {
    constructor(fetched: Sleep) : this(
        minutesInBed = fetched.minutesInBed,
        minutesAsleep = fetched.minutesAsleep,
        efficiency = fetched.efficiency,
        startTime = ZonedDateTime.of(fetched.startTime, ZoneId.systemDefault()),
        endTime = ZonedDateTime.of(fetched.endTime, ZoneId.systemDefault()),
    )

    fun toDto() = SleepModuleDto(
        minutesInBed = this.minutesInBed,
        minutesAsleep = this.minutesAsleep,
        efficiency = this.efficiency,
        startTime = this.startTime,
        endTime = this.endTime,
    )
}