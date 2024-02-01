package com.example.notice.domain.post.dto.request

import com.example.notice.domain.post.model.PostStatus

data class UpdatePostRequest (
    val title: String,
    val description: String,
    val status: PostStatus,
    val postImageUrl: String,
)