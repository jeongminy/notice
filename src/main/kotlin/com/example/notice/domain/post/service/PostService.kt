package com.example.notice.domain.post.service

import com.example.notice.domain.post.dto.AddPostRequest
import com.example.notice.domain.post.dto.PostResponse
import com.example.notice.infra.security.UserPrincipal

interface PostService {

    fun getPosts(): List<PostResponse>

    fun getPostById(postId: Long): PostResponse

    fun addPost(addPostRequest: AddPostRequest, userPrincipal: UserPrincipal): PostResponse
}