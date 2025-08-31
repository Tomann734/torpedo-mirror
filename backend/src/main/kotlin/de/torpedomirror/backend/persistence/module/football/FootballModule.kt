package de.torpedomirror.backend.persistence.module.football

import de.torpedomirror.backend.dto.module.FootballModuleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.ZonedDateTime

@Entity
class FootballModule(
    module: Module,

    recordTime: ZonedDateTime,

    @Column(name = "team_id", nullable = false, updatable = false)
    val teamId: Int,

    @Column(name = "home_team", nullable = false, updatable = false)
    val homeTeam: String,

    @Column(name = "away_team", nullable = false, updatable = false)
    val awayTeam: String,

    @Column(name = "stadium_name", nullable = false, updatable = false)
    val stadiumName: String,

    @Column(name = "competition", nullable = false, updatable = false)
    val competition: String,

    @Column(name = "kickoff_time", nullable = false, updatable = false)
    val kickoffTime: ZonedDateTime,
) : Submodule(
    module = module,
    recordTime = recordTime
) {
    override fun toDto(): FootballModuleDto = FootballModuleDto(
        name = this.module.name,
        type = this.module.type,
        recordTime = this.recordTime,
        teamId = this.teamId,
        homeTeam = this.homeTeam,
        awayTeam = this.awayTeam,
        stadiumName = this.stadiumName,
        competition = this.competition,
        kickoffTime = this.kickoffTime
    )
}