package io.insource.api.demo.controller

import io.insource.api.v1.posts.Post
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["posts"])
@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class PostsController {
  @ApiOperation("Get the most recent posts")
  @GetMapping("/posts")
  fun posts(): List<Post> = ArrayList<Post>().also { posts ->
    Post().also { post ->
      post.id = "123"
      post.title = "First post, what's up guys???"
      post.preview = "I just wanted to say hi and..."
      post.content = "<div>I just wanted to say hi and...</div>"
      post.image = "https://www.domain.com/images/image.jpg"
      post.media = "https://youtube.com/embed/_1234"
      post.postedDate = "2018-09-07T23:12:00Z"
      post.postedBy = "CoolGuy123"
      post.replies = 11
      post.upVotes = 247

      posts.add(post)
    }
  }
}