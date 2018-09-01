package com.reddit.api

import com.reddit.ApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.web.client.RestTemplate

@Configuration
@PropertySource("classpath:reddit.properties")
class RedditApiConfiguration(
  @Value("\${basePath}")
  private val basePath: String
) {
  @Bean fun restTemplate() = RestTemplate()
  @Bean fun apiClient() = ApiClient(restTemplate()).also {
    it.basePath = basePath
  }
  @Bean fun redditApi() = RedditApi(apiClient())
}