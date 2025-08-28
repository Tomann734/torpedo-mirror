package de.torpedomirror.backend.exception

class ModuleAlreadyExistsException(
    name: String
) : RuntimeException("module with name $name already exists")