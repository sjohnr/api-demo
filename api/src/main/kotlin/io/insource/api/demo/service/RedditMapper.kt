package io.insource.api.demo.service

import com.reddit.r.subreddit.Child
import io.insource.api.v1.posts.Post
import org.springframework.stereotype.Component
import org.springframework.web.util.HtmlUtils
import java.time.Instant

@Component
class RedditMapper {
  fun mapPost(child: Child) = Post().also { post ->
    val data = child.childData
    post.id = data.id
    post.title = data.title
    post.preview = data.selftext?.let {
      when {
        it.contains('\n') -> it.substringBefore('\n')
        it.contains('[') -> it.substringBefore('[')
        else -> it
      }
    }?.let {
      if (it.length > 48) "${it.substring(0, 48).trim()}..." else it
    }?.let {
      if (it.isNotEmpty()) it else null
    }
    post.content = data.selftextHtml?.let {
      HtmlUtils.htmlUnescape(it)
    } ?: data.bodyHtml?.let {
      HtmlUtils.htmlUnescape(it)
    }
    post.imageUrl = data.preview?.let {
      if (it.images.size > 0) it.images[0].source.url else null
    } ?: data.thumbnail?.let {
      if (it.startsWith("http")) it else null
    }
    post.mediaUrl = data.secureMedia?.redditVideo?.fallbackUrl
      ?: data.secureMedia?.oembed?.html?.substringAfter(" src=\"")?.substringBefore("\"")
    post.postedBy = data.author
    post.postedDate = data.createdUtc?.toLong()?.let {
      Instant.ofEpochSecond(it).toString()
    }
    post.replies = data.numComments
    post.upVotes = data.ups
  }
}