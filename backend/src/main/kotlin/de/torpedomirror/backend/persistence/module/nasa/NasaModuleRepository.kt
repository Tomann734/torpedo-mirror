package de.torpedomirror.backend.persistence.module.nasa

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface NasaModuleRepository : CrudRepository<NasaModule, UUID> {
}