package com.example.notice.domain.post.dto.response

import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.model.QPostEntity.postEntity
import java.time.LocalDateTime

data class PostResponse (
    val id: Long,
    val nickname: String,
    val title: String,
    val description: String,
    val status: PostStatus,
    val postImageUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

