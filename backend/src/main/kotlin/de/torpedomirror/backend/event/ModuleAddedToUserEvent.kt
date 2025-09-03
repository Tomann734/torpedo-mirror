package de.torpedomirror.backend.event

data class ModuleAddedToUserEvent(
    val username: String,
    val moduleName: String,
)
