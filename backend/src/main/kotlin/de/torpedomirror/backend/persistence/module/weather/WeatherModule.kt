package de.torpedomirror.backend.persistence.module.weather

import de.torpedomirror.backend.dto.module.WeatherModuleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.ZonedDateTime

@Entity
class WeatherModule(
    module: Module,

    recordTime: ZonedDateTime,

    @Column(name = "latitude", nullable = false, updatable = false)
    val latitude: Double,

    @Column(name = "longitude", nullable = false, updatable = false)
    val longitude: Double,

    @Column(name = "is_day", nullable = false, updatable = false)
    val isDay: Boolean,

    @Column(name = "current_temperature", nullable = false, updatable = false)
    val currentTemperature: Double,

    @Column(name = "current_rain", nullable = false, updatable = false)
    val currentRain: Double,

    @Column(name = "current_shower", nullable = false, updatable = false)
    val currentShower: Double,

    @Column(name = "current_snow", nullable = false, updatable = false)
    val currentSnow: Double,

    @Column(name = "current_cloud_cover", nullable = false, updatable = false)
    val currentCloudCover: Int,

    @Column(name = "min_temperature", nullable = false, updatable = false)
    val minTemperature: Double,

    @Column(name = "max_temperature", nullable = false, updatable = false)
    val maxTemperature: Double,

    @Column(name = "min_rain", nullable = false, updatable = false)
    val minRain: Double,

    @Column(name = "max_rain", nullable = false, updatable = false)
    val maxRain: Double,

    @Column(name = "min_shower", nullable = false, updatable = false)
    val minShower: Double,

    @Column(name = "max_shower", nullable = false, updatable = false)
    val maxShower: Double,

    @Column(name = "min_snow", nullable = false, updatable = false)
    val minSnow: Double,

    @Column(name = "max_snow", nullable = false, updatable = false)
    val maxSnow: Double,

    @Column(name = "min_cloud_coverage", nullable = false, updatable = false)
    val minCloudCoverage: Int,

    @Column(name = "max_cloud_coverage", nullable = false, updatable = false)
    val maxCloudCoverage: Int,
) : Submodule(
    module = module,
    recordTime = recordTime
) {
    override fun toDto(): WeatherModuleDto = WeatherModuleDto(
        name = module.name,
        type = this::class.simpleName!!,
        latitude = latitude,
        longitude = longitude,
        isDay = isDay,
        currentTemperature = currentTemperature,
        isPrecipitating = (currentRain > 0.0 || currentShower > 0.0 || currentSnow > 0.0),
        isCloudy = currentCloudCover > 20,
        maxTemperature = maxTemperature,
        minTemperature = minTemperature,
    )
}