package io.insource.api.demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import us.sportradar.api.official.ApiClient
import us.sportradar.api.official.NflOfficialApi
import javax.annotation.PostConstruct

@Configuration
@PropertySource("classpath:sportradar.properties")
class SportradarConfiguration(
  val apiClient: ApiClient,
  val nflOfficialApi: NflOfficialApi
) {
  @Value("\${basePath}")
  lateinit var basePath: String

  @Value("\${officialApiKey}")
  lateinit var officialApiKey: String

  @PostConstruct
  fun init() {
    apiClient.basePath = basePath
    nflOfficialApi.apiKey = officialApiKey
  }
}