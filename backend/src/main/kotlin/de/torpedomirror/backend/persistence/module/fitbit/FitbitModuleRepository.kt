package de.torpedomirror.backend.persistence.module.fitbit

import org.springframework.data.repository.CrudRepository
import java.util.*

interface FitbitModuleRepository : CrudRepository<FitbitModule, UUID> {

}