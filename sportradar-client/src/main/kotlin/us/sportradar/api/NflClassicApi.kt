package us.sportradar.api

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import us.sportradar.api.nfl.teams.RosterResponse
import javax.annotation.PostConstruct

@Component
class NflClassicApi(private val apiClient: ApiClient = ApiClient()) {
  lateinit var apiKey: String

  @PostConstruct
  fun initialize() {
    apiClient.queryParamsEnhancer = { queryParams ->
      queryParams["api_key"] = apiKey
    }
  }

  fun roster(team: String): RosterResponse {
    return apiClient.invoke("/nfl-t1/teams/$team/roster.json", HttpMethod.GET, RosterResponse::class)
  }
}