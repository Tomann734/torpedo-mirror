package de.torpedomirror.backend.dto.module

import java.time.ZonedDateTime

data class PersonalPictureModuleDto(
    override val name: String,
    override val type: String,
    override val recordTime: ZonedDateTime,
    val fileName: String,
) : SubmoduleDto
