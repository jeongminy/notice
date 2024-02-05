package com.example.notice.domain.comment.dto.response

import java.time.LocalDateTime

data class CommentResponse (
    val id: Long,
    val nickname: String,
    val comment: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)