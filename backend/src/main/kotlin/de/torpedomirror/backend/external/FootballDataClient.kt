package de.torpedomirror.backend.external

import de.torpedomirror.backend.dto.footballdata.Match
import de.torpedomirror.backend.dto.footballdata.MatchesResponse
import de.torpedomirror.backend.dto.footballdata.Team
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class FootballDataClient(
    private val footballDataWebClient: WebClient,
) {
    fun getNextMatch(teamId: Int): Match {
        val res = footballDataWebClient
            .get()
            .uri {
                it.path("teams/$teamId/matches")
                it.queryParam("status", "SCHEDULED")
                it.queryParam("limit", "1")
                it.build()
            }
            .retrieve()
            .bodyToMono(MatchesResponse::class.java)
            .block()!!

        return res.matches.first()
    }

    fun getHomeTeamInformation(teamId: Int): Team {
        val res = footballDataWebClient
            .get()
            .uri {
                it.path("teams/$teamId")
                it.build()
            }
            .retrieve()
            .bodyToMono(Team::class.java)
            .block()!!

        return res
    }
}