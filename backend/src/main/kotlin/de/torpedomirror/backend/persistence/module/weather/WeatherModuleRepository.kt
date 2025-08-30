package de.torpedomirror.backend.persistence.module.weather

import org.springframework.data.repository.CrudRepository
import java.util.*

interface WeatherModuleRepository : CrudRepository<WeatherModule, UUID> {

}
