package io.insource.api.demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import us.sportradar.api.ApiClient
import us.sportradar.api.NflClassicApi
import us.sportradar.api.NflOfficialApi
import javax.annotation.PostConstruct

@Configuration
@PropertySource("classpath:sportradar.properties")
class SportradarConfiguration(
  val apiClient: ApiClient,
  val nflClassicApi: NflClassicApi,
  val nflOfficialApi: NflOfficialApi
) {
  @Value("\${basePath}")
  lateinit var basePath: String

  @Value("\${classicApiKey}")
  lateinit var classicApiKey: String

  @Value("\${officialApiKey}")
  lateinit var officialApiKey: String

  @PostConstruct
  fun init() {
    apiClient.basePath = basePath
    nflClassicApi.apiKey = classicApiKey
    nflOfficialApi.apiKey = officialApiKey
  }
}