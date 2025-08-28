package de.torpedomirror.backend.exception

class MirrorUserNotFoundException(
    name: String
) : RuntimeException("mirror user with name $name not found")