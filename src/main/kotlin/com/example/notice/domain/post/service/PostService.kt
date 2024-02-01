package com.example.notice.domain.post.service

import com.example.notice.domain.post.dto.PostResponse

interface PostService {

    fun getPosts(): List<PostResponse>
}