package de.torpedomirror.backend.persistence.module.base

import org.springframework.data.repository.CrudRepository
import java.util.*

interface SubmoduleRepository : CrudRepository<Submodule, UUID> {
    fun findFirstByModuleNameOrderByRecordTime(moduleName: String): Submodule?
}