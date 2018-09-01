package com.reddit.api

import com.reddit.r.subreddit.SubredditResponse
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class RedditApi(private val apiClient: ApiClient = ApiClient()) {
  fun subreddit(subreddit: String, limit: Int = 25): SubredditResponse {
    val queryParams = apiClient.params().apply {
      add("limit", limit.toString())
    }
    return apiClient.invoke("/r/$subreddit", HttpMethod.GET, SubredditResponse::class, queryParams)
  }

  fun subredditSearch(
    subreddit: String,
    q: String,
    limit: Int = 25,
    sort: String = "new",
    t: String = "all"
  ): SubredditResponse {
    val queryParams = apiClient.params().apply {
      add("q", q)
      add("limit", limit.toString())
      add("sort", sort)
      add("t", t)
    }
    return apiClient.invoke("/r/$subreddit/search", HttpMethod.GET, SubredditResponse::class, queryParams)
  }

  fun comments(
    id: String,
    context: Int = 1,
    showEdits: Boolean = true,
    showMore: Boolean = true,
    sort: String = "new",
    threaded: Boolean = true,
    truncate: Int = 50,
    depth: Int = 1,
    limit: Int = 25
  ): List<SubredditResponse> {
    val queryParams = apiClient.params().apply {
      add("context", context.toString())
      add("showedits", showEdits.toString())
      add("showmore", showMore.toString())
      add("sort", sort)
      add("threaded", threaded.toString())
      add("truncate", truncate.toString())
      add("depth", depth.toString())
      add("limit", limit.toString())
    }
    return apiClient.invoke("/comments/$id", HttpMethod.GET, Array<SubredditResponse>::class, queryParams).toList()
  }
}