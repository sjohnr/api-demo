package io.insource.api.demo.controller

import io.insource.api.demo.service.NflOfficialMapper
import io.insource.api.v1.teams.Team
import io.insource.api.v1.teams.TeamSummary
import io.insource.api.v1.teams.players.Player
import io.insource.api.v1.teams.players.PlayerSummary
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import us.sportradar.api.official.NflOfficialApi

@Api(tags = ["teams"])
@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class TeamsController(
  val nflOfficialApi: NflOfficialApi,
  val nflOfficialMapper: NflOfficialMapper
) {
  @ApiOperation("Get a list of teams.")
  @GetMapping("/teams")
  fun teams(): List<TeamSummary> = nflOfficialApi.league().let {
    nflOfficialMapper.mapTeamSummary(it)
  }

  @ApiOperation("Get information about a team.")
  @GetMapping("/teams/{teamId}")
  fun team(@PathVariable teamId: String): Team = nflOfficialApi.team(teamId).let { profileResponse ->
    nflOfficialMapper.mapTeam(profileResponse)
  }

  @ApiOperation("Get a list of a team's players.")
  @GetMapping("/teams/{teamId}/players")
  fun players(@PathVariable teamId: String): List<PlayerSummary> = nflOfficialApi.team(teamId).let { profileResponse ->
    nflOfficialMapper.mapPlayerSummary(profileResponse)
  }

  @ApiOperation("Get information about a player.")
  @GetMapping("/teams/{teamId}/players/{playerId}")
  fun player(@PathVariable teamId: String, @PathVariable playerId: String): Player = nflOfficialApi.player(playerId).let { playerResponse ->
    nflOfficialMapper.mapPlayer(playerResponse)
  }
}