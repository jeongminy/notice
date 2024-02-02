package com.example.notice.domain.post.service

import com.example.notice.domain.post.dto.request.AddPostRequest
import com.example.notice.domain.post.dto.request.UpdatePostRequest
import com.example.notice.domain.post.dto.response.PostDetailResponse
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.infra.security.UserPrincipal
import org.springframework.http.ResponseEntity

interface PostService {

    fun getPosts(): List<PostResponse>

    fun getPostById(postId: Long, userPrincipal:UserPrincipal): PostDetailResponse

    fun addPost(addPostRequest: AddPostRequest, userPrincipal: UserPrincipal): PostResponse

    fun updatePost(postId: Long, updatePostRequest: UpdatePostRequest, userPrincipal: UserPrincipal): PostResponse

    fun deletePost(postId: Long, userPrincipal: UserPrincipal)
}