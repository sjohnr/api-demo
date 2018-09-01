package io.insource.api.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.reddit.api.RedditApi
import io.insource.api.demo.service.RedditMapper
import io.insource.api.v1.posts.Post
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.KClass

@Api(tags = ["posts"])
@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class PostsController(
  val redditApi: RedditApi,
  val redditMapper: RedditMapper
) {
  @GetMapping("/posts")
  @ApiOperation("Get the most recent posts.")
  fun posts(): List<Post> = redditApi.subreddit("Patriots").let {
    redditMapper.mapSubreddit(it)
  }

  @GetMapping("/posts/{id}")
  @ApiOperation("Get the most recent comments for a post.")
  fun comments(@PathVariable id: String): List<Post> = redditApi.comments(id).let {
    redditMapper.mapComments(it)
  }
}

fun <T : Any> readResource(name: String, resourceClass: KClass<T>): T =
  ObjectMapper().readValue(ClassPathResource(name).url, resourceClass.java)
