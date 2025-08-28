package de.torpedomirror.backend.persistence.module.base

import org.springframework.data.repository.CrudRepository

interface ModuleRepository : CrudRepository<Module, String> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): Module?
    fun deleteByName(name: String)
}