package de.torpedomirror.backend.dto.module

import java.time.ZonedDateTime

data class NasaModuleDto(
    override val name: String,
    override val type: String,
    override val recordTime: ZonedDateTime,
    val title: String,
    val description: String,
    val fileName: String,
) : SubmoduleDto
