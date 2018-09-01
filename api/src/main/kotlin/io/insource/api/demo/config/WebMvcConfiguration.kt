package io.insource.api.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
@ComponentScan("com.reddit.api")
class WebMvcConfiguration {
  @Bean
  fun propertySourcesPlaceholderConfigurer() = PropertySourcesPlaceholderConfigurer()
}