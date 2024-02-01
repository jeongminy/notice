package com.example.notice.domain.comment.dto.response

import java.time.LocalDateTime

data class CommentResponse (
    val nickname: String,
    val comment: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)