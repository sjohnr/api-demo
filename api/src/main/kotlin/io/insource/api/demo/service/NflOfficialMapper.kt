package io.insource.api.demo.service

import io.insource.api.v1.teams.Team
import io.insource.api.v1.teams.TeamSummary
import io.insource.api.v1.teams.players.Player
import io.insource.api.v1.teams.players.PlayerSummary
import org.springframework.stereotype.Component
import us.sportradar.api.nfl.league.LeagueResponse
import us.sportradar.api.nfl.players.PlayerResponse
import us.sportradar.api.nfl.teams.ProfileResponse

@Component
class NflOfficialMapper {
  fun mapTeamSummary(leagueResponse: LeagueResponse) = leagueResponse.conferences.flatMap { it.divisions }.flatMap { it.teams }.map { teamResponse ->
    mapTeamSummary(teamResponse)
  }

  fun mapTeamSummary(teamResponse: us.sportradar.api.nfl.league.Team) = TeamSummary().also { team ->
    team.id = teamResponse.id
    team.name = teamResponse.name
    team.market = teamResponse.market
  }

  fun mapTeam(profileResponse: ProfileResponse) = Team().also { team ->
    team.id = profileResponse.id
    team.name = profileResponse.name
    team.market = profileResponse.market
    team.stadium = profileResponse.venue.name
    team.city = profileResponse.venue.city
    team.state = profileResponse.venue.state
    team.conference = profileResponse.conference.name
    team.division = profileResponse.division.name
    team.headCoach = profileResponse.coaches.firstOrNull {
      it.position == "Head Coach"
    }?.fullName
    team.offensiveCoordinator = profileResponse.coaches.firstOrNull {
      it.position == "Offensive Coordinator"
    }?.fullName
  }

  fun mapPlayerSummary(profileResponse: ProfileResponse) = profileResponse.players.map { playerResponse ->
    mapPlayerSummary(playerResponse)
  }

  fun mapPlayerSummary(playerResponse: us.sportradar.api.nfl.teams.Player) = PlayerSummary().also { player ->
    player.id = playerResponse.id
    player.name = playerResponse.name
    player.number = playerResponse.jersey
    player.position = playerResponse.position
  }

  fun mapPlayer(playerResponse: PlayerResponse) = Player().also { player ->
    player.id = playerResponse.id
    player.name = playerResponse.name
    player.number = playerResponse.jersey
    player.weight = playerResponse.weight
    player.height = playerResponse.height
    player.birthDate = playerResponse.birthDate
    player.position = playerResponse.position
    player.team = "${playerResponse.team.market} ${playerResponse.team.name}"
    player.college = playerResponse.college
    player.rookieYear = playerResponse.rookieYear
    player.draftYear = playerResponse.draft.year
    player.draftRound = playerResponse.draft.round
    player.draftPick = playerResponse.draft.number
    player.draftTeam = "${playerResponse.draft.team.market} ${playerResponse.draft.team.name}"
    player.seasons = playerResponse.seasons.filter {
      it.type == "REG"
    }.size
  }
}