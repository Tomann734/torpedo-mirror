package de.torpedomirror.backend.service

import de.torpedomirror.backend.exception.FileNotFoundException
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.Random
import java.util.stream.Collectors

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

    fun getRandomFileNameOfDirectory(
        directoryPath: Path,
    ): String {
        val files = Files.list(directoryPath)
            .filter { path: Path -> Files.isRegularFile(path) }
            .collect(Collectors.toList())

        if (files.isEmpty()) throw FileNotFoundException("")

        val random = Random()
        val randomFile = files[random.nextInt(files.size)]
        return randomFile.fileName.toString()
    }
}