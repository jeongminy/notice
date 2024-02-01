package com.example.notice.domain.post.dto

import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatusEntity
import com.example.notice.domain.user.model.UserEntity

data class PostResponse (
    val id: Long,
    val nickname: String,
    val title: String,
    val description: String,
    val status: PostStatusEntity,
    val postImageUrl: String
)

fun PostEntity.toResponse(): PostResponse {
    return PostResponse(
        id = id!!,
        nickname = user.profile.nickname,
        title = title,
        description = description,
        status = status,
        postImageUrl = postImageUrl
    )
}