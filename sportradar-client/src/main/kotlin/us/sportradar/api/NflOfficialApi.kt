package us.sportradar.api

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import us.sportradar.api.nfl.players.PlayerResponse
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

  fun player(id: String): PlayerResponse {
    return apiClient.invoke("/nfl/official/trial/v5/en/players/$id/profile.json", HttpMethod.GET, PlayerResponse::class)
  }
}