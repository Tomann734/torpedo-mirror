package de.torpedomirror.backend.dto.module

import de.torpedomirror.backend.dto.module.wikimedia.WikimediaFactDto
import java.time.ZonedDateTime

data class WikimediaModuleDto(
    override val name: String,
    override val type: String,
    override val recordTime: ZonedDateTime,
    val facts: List<WikimediaFactDto>
) : SubmoduleDto
