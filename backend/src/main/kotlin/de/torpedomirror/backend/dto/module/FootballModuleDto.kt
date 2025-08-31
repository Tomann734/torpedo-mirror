package de.torpedomirror.backend.dto.module

import java.time.ZonedDateTime

data class FootballModuleDto(
    override val name: String,
    override val type: String,
    override val recordTime: ZonedDateTime,
    val teamId: Int,
    val homeTeam: String,
    val awayTeam: String,
    val stadiumName: String,
    val competition: String,
    val kickoffTime: ZonedDateTime,
) : SubmoduleDto
