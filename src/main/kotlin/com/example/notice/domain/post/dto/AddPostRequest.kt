package com.example.notice.domain.post.dto

data class AddPostRequest(
    val title: String,
    val description: String,
    val postImageUrl: String
)