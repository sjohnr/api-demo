package io.insource.api.demo.controller

import com.reddit.api.RedditApi
import io.insource.api.demo.service.RedditMapper
import io.insource.api.v3.posts.Post
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["posts"])
@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class PostsController(
  val redditApi: RedditApi,
  val redditMapper: RedditMapper
) {
  @ApiOperation("Get the most recent posts.")
  @GetMapping("/posts")
  fun posts(): List<Post> =
    redditApi.subreddit("guildwars2", 10).data.children.map(redditMapper::mapPost)

  @ApiOperation("Get the most recent comments for a post.")
  @GetMapping("/posts/{id}")
  fun comments(@PathVariable("id") id: String): List<Post> =
    redditApi.comments(id)[1].data.children.map(redditMapper::mapPost)
}