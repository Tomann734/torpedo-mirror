package de.torpedomirror.backend.persistence.module.base

import org.springframework.data.repository.CrudRepository
import java.util.*

interface SubmoduleRepository : CrudRepository<Submodule, UUID> {
    fun findFirstByModuleNameOrderByRecordTimeDesc(moduleName: String): Submodule?
}