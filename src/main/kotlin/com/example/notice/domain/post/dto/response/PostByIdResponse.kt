package com.example.notice.domain.post.dto.response

import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.post.model.PostStatus
import java.time.LocalDateTime

data class PostByIdResponse (
    val id: Long,
    val nickname: String,
    val title: String,
    val description: String,
    val status: PostStatus,
    val postImageUrl: String,
    val likeCount: Int,
    val likedByCurrentUser: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val comments: List<CommentResponse>
)

