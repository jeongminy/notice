package com.example.notice.domain.post.dto.request

import jakarta.validation.constraints.Size

data class AddPostRequest(


    @Size(min = 1, max = 500, message = "제목은 1자 이상 500자 이하로 작성해주세요")
    val title: String,

    @Size(min = 1, max = 500, message = "제목은 1자 이상 5000자 이하로 작성해주세요")
    val description: String,

    val postImageUrl: String,
)