package com.example.notice.domain.post.dto.response

import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus

data class PostResponse (
    val id: Long,
    val nickname: String,
    val title: String,
    val description: String,
    val status: PostStatus,
    val postImageUrl: String
)

