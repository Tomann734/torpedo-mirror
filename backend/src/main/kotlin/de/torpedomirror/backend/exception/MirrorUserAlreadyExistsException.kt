package de.torpedomirror.backend.exception

class MirrorUserAlreadyExistsException(
    name: String
) : RuntimeException("mirror user with name $name already exists")