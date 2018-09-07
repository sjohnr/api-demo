package io.insource.api.demo.service

import com.reddit.r.subreddit.Child
import com.reddit.r.subreddit.SubredditResponse
import io.insource.api.v1.posts.Post
import io.insource.api.v1.teams.Team
import io.insource.api.v1.teams.TeamSummary
import io.insource.api.v1.teams.players.Detail
import io.insource.api.v1.teams.players.Mention
import io.insource.api.v1.teams.players.Player
import io.insource.api.v1.teams.players.PlayerSummary
import org.springframework.stereotype.Component
import org.springframework.web.util.HtmlUtils
import us.sportradar.api.nfl.league.LeagueResponse
import us.sportradar.api.nfl.players.PlayerResponse
import us.sportradar.api.nfl.teams.ProfileResponse
import java.time.Instant
import java.time.LocalDate

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
    player.position = playerResponse.position
    player.image = "https://www.nfl.com/images/players/${playerResponse.firstName}${playerResponse.lastName}.jpg"
    addDetail(player, "Team", "${playerResponse.team.market} ${playerResponse.team.name}")
    addDetail(player, "Height", (playerResponse.height / 12).toString() + "' " + (playerResponse.height % 12).toString() + "\"")
    addDetail(player, "Weight", playerResponse.weight.toString())
    addDetail(player, "Age", (LocalDate.now().year - LocalDate.parse(playerResponse.birthDate).year).toString())
    addDetail(player, "College", playerResponse.college)
    addDetail(player, "Years", playerResponse.seasons.filter {
      it.type == "REG"
    }.size.toString())
  }

  fun addDetail(player: Player, name: String, value: String) {
    player.details.add(Detail().also {
      it.name = name
      it.value = value
    })
  }

  fun mapSubreddit(subredditResponse: SubredditResponse) =
    subredditResponse.data.children.map(::mapMention)

  fun mapComments(commentsResponse: List<SubredditResponse>) =
    commentsResponse[1].data.children.map(::mapMention)

  fun mapMention(child: Child) = Mention().also { mention ->
    val data = child.childData
    mention.id = data.id
    mention.title = data.title
    mention.preview = data.selftext?.let {
      when {
        it.contains('\n') -> it.substringBefore('\n')
        it.contains('[') -> it.substringBefore('[')
        else -> it
      }
    }?.let {
      if (it.length > 48) "${it.substring(0, 48).trim()}..." else it
    }?.let {
      if (it.isNotEmpty()) it else null
    }
    mention.content = data.selftextHtml?.let {
      HtmlUtils.htmlUnescape(it)
    } ?: data.bodyHtml?.let {
      HtmlUtils.htmlUnescape(it)
    }
    mention.imageUrl = data.preview?.let {
      if (it.images.size > 0) it.images[0].source.url else null
    } ?: data.thumbnail?.let {
      if (it.startsWith("http")) it else null
    }
    mention.mediaUrl = data.secureMedia?.redditVideo?.fallbackUrl
      ?: data.secureMedia?.oembed?.html?.substringAfter(" src=\"")?.substringBefore("\"")
    mention.postedBy = data.author
    mention.postedDate = data.createdUtc?.toLong()?.let {
      Instant.ofEpochSecond(it).toString()
    }
    mention.replies = data.numComments
    mention.upVotes = data.ups
  }
}