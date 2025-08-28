package de.torpedomirror.backend.exception

class ModuleNotFoundException(
    name: String
) : RuntimeException("module with name $name not found")