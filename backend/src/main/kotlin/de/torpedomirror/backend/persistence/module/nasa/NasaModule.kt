package de.torpedomirror.backend.persistence.module.nasa

import de.torpedomirror.backend.dto.module.NasaModuleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDate
import java.time.ZonedDateTime

@Entity
class NasaModule(
    module: Module,

    recordTime: ZonedDateTime,

    @Column(name = "title", nullable = false, updatable = false)
    val title: String,

    @Column(name = "description", nullable = false, updatable = false)
    val description: String,

    @Column(name = "date", nullable = false, updatable = false)
    val date: LocalDate,

    @Column(name = "url", nullable = false, updatable = false)
    val url: String,

    @Column(name = "file_name", nullable = false, updatable = false)
    val fileName: String,
) : Submodule(
    module = module,
    recordTime = recordTime,
) {
    override fun toDto(): NasaModuleDto = NasaModuleDto(
        name = this.module.name,
        type = this.module.type,
        recordTime = this.recordTime,
        title = this.title,
        description = this.description,
        fileName = this.fileName
    )
}