package com.example.notice.domain.post.dto.response

import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import java.time.LocalDateTime

data class PostDetailResponse (
    val id: Long,
    val nickname: String,
    val title: String,
    val description: String,
    val status: PostStatus,
    val postImageUrl: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val comments: List<CommentResponse>
)

