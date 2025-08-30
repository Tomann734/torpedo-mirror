package de.torpedomirror.backend.persistence.module.football

import org.springframework.data.repository.CrudRepository
import java.util.*

interface FootballModuleRepository : CrudRepository<FootballModule, UUID> {

}