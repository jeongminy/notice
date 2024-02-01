package com.example.notice.domain.post.service

import com.example.notice.domain.post.dto.PostResponse
import org.springframework.http.ResponseEntity

interface PostService {

    fun getPosts(): List<PostResponse>

    fun getPostById(postId: Long): PostResponse
}