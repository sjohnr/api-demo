package io.insource.api.demo.controller

import io.insource.api.v1.teams.Team
import io.insource.api.v1.teams.players.Player
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["teams"])
@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class TeamsController {
  @ApiOperation("Get information about a team.")
  @GetMapping("/teams/{teamId}")
  fun team(@PathVariable("teamId") id: String): Team = readResource("team.json", Team::class)

  @ApiOperation("Get a list of a team's players.")
  @GetMapping("/teams/{teamId}/players")
  fun players(@PathVariable("teamId") id: String): List<Player> = ArrayList<Player>().also {
    it.add(readResource("player.json", Player::class))
    it.add(readResource("player.json", Player::class))
  }

  @ApiOperation("Get information about a player.")
  @GetMapping("/teams/{teamId}/players/{playerId}")
  fun player(@PathVariable("teamId") teamId: String, @PathVariable("playerId") playerId: String): Player = readResource("player.json", Player::class)
}