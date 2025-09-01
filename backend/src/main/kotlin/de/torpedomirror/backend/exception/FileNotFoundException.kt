package de.torpedomirror.backend.exception

class FileNotFoundException(
    name: String
) : RuntimeException("file with name $name not found")