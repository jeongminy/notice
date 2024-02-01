package com.example.notice.domain.post.dto.request

import com.example.notice.domain.post.model.PostStatus
import jakarta.validation.constraints.Size

data class UpdatePostRequest (

    @Size(min = 1, max = 500, message = "제목은 1자 이상 500자 이하로 작성해주세요")
    val title: String,

    @Size(min = 1, max = 500, message = "제목은 1자 이상 5000자 이하로 작성해주세요")
    val description: String,

    val status: PostStatus,

    val postImageUrl: String,
)