package io.insource.api.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.insource.api.v1.posts.Post
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.KClass

@Api(tags = ["posts"])
@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class PostsController {
  @ApiOperation("Get the most recent posts")
  @GetMapping("/posts")
  fun posts(): List<Post> =
    readResource("sample.json", Array<Post>::class).toList()
}

fun <T : Any> readResource(name: String, resourceClass: KClass<T>): T =
  ObjectMapper().readValue(ClassPathResource(name).url, resourceClass.java)
