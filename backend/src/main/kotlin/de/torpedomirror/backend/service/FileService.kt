package de.torpedomirror.backend.service

import de.torpedomirror.backend.exception.FileNotFoundException
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

@Service
class FileService {

    fun saveFile(
        directoryPath: Path,
        fileName: String,
        data: ByteArray
    ) {
        Files.createDirectories(directoryPath)
        val fullFilePath = directoryPath.resolve(fileName)
        Files.write(
            fullFilePath,
            data,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        )
    }

    fun readFile(
        directoryPath: Path,
        fileName: String
    ): ByteArray {
        val filePath = directoryPath.resolve(fileName)
        if (!Files.exists(filePath)) throw FileNotFoundException(fileName)
        return Files.readAllBytes(filePath)
    }
}