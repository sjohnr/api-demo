package com.reddit.oauth

import com.reddit.ApiClient
import com.reddit.api.RedditApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@Configuration
@EnableOAuth2Client
@PropertySource("classpath:reddit.properties")
class RedditOAuthConfiguration(
  @Value("\${clientId}")
  private val clientId: String,

  @Value("\${clientSecret}")
  private val clientSecret: String,

  @Value("\${accessTokenUri}")
  private val accessTokenUri: String,

  @Value("\${basePath}")
  private val basePath: String,

  @Autowired
  private val clientContext: OAuth2ClientContext
) {
  @Bean fun redditApi() = RedditApi(apiClient())

  @Bean fun apiClient() = ApiClient(redditRestTemplate()).also {
    it.basePath = basePath
  }

  @Bean fun redditRestTemplate() = OAuth2RestTemplate(reddit(), clientContext).also {
    it.setAccessTokenProvider(ClientCredentialsAccessTokenProvider())
  }

  @Bean fun reddit() = ClientCredentialsResourceDetails().also {
    it.id = "reddit"
    it.clientId = clientId
    it.clientSecret = clientSecret
    it.scope = arrayListOf("identity")
    it.accessTokenUri = accessTokenUri
  }
}