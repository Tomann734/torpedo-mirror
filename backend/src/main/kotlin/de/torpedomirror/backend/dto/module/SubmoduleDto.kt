package de.torpedomirror.backend.dto.module

import java.time.ZonedDateTime

interface SubmoduleDto {
    val name: String
    val type: String
    val recordTime: ZonedDateTime
}