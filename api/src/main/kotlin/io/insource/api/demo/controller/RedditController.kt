package io.insource.api.demo.controller

import com.reddit.api.v1.me.MeResponse
import com.reddit.r.subreddit.SubredditResponse
import io.swagger.annotations.Api
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["reddit"])
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class RedditController {
  @GetMapping("/api/v1/me")
  fun me() = MeResponse()

  @GetMapping("/r/{subreddit}")
  fun subreddit(@PathVariable("subreddit") subreddit: String) = SubredditResponse()

  @GetMapping("/r/{subreddit}/search")
  fun subredditSearch(
    @PathVariable("subreddit") subreddit: String,
    @RequestParam("q") q: String,
    @RequestParam("limit", defaultValue = "25") limit: Int,
    @RequestParam("sort", defaultValue = "new") sort: String,
    @RequestParam("t", defaultValue = "all") t: String) = SubredditResponse()
}