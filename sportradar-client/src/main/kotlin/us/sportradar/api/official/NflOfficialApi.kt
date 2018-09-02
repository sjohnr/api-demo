package us.sportradar.api.official

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import us.sportradar.api.nfl.league.LeagueResponse
import us.sportradar.api.nfl.players.PlayerResponse
import us.sportradar.api.nfl.teams.ProfileResponse
import javax.annotation.PostConstruct

@Component
class NflOfficialApi(private val apiClient: ApiClient = ApiClient()) {
  lateinit var apiKey: String

  @PostConstruct
  fun initialize() {
    apiClient.queryParamsEnhancer = { queryParams ->
      queryParams["api_key"] = apiKey
    }
  }

  fun league(): LeagueResponse {
    return apiClient.invoke("/nfl/official/trial/v5/en/league/hierarchy.json", HttpMethod.GET, LeagueResponse::class)
  }

  fun team(id: String): ProfileResponse {
    return apiClient.invoke("/nfl/official/trial/v5/en/teams/$id/profile.json", HttpMethod.GET, ProfileResponse::class)
  }

  fun player(id: String): PlayerResponse {
    return apiClient.invoke("/nfl/official/trial/v5/en/players/$id/profile.json", HttpMethod.GET, PlayerResponse::class)
  }
}