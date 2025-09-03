package de.torpedomirror.backend.persistence.module.personalpicture

import de.torpedomirror.backend.dto.module.PersonalPictureModuleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.ZonedDateTime

@Entity
class PersonalPictureModule(
    module: Module,

    recordTime: ZonedDateTime,

    @Column(name = "file_name", nullable = false, updatable = false)
    val fileName: String,
) : Submodule(
    module = module,
    recordTime = recordTime,
) {
    override fun toDto(): PersonalPictureModuleDto = PersonalPictureModuleDto(
        name = this.module.name,
        type = this.module.type,
        recordTime = this.recordTime,
        fileName = this.fileName
    )
}