package io.insource.api.demo.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ClassPathResource
import kotlin.reflect.KClass

object ClassPathUtils {
  fun <T : Any> readResource(name: String, resourceClass: KClass<T>): T =
    ObjectMapper().readValue(ClassPathResource(name).url, resourceClass.java)
}