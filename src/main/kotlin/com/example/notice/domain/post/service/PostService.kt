package com.example.notice.domain.post.service

import com.example.notice.domain.post.dto.request.AddPostRequest
import com.example.notice.domain.post.dto.request.UpdatePostRequest
import com.example.notice.domain.post.dto.response.PostByIdResponse
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.infra.security.UserPrincipal

interface PostService {

    fun getAllPosts(): List<PostResponse>

    fun getPostById(postId: Long, userPrincipal:UserPrincipal): PostByIdResponse

    fun addPost(addPostRequest: AddPostRequest, userPrincipal: UserPrincipal): PostResponse

    fun updatePost(postId: Long, updatePostRequest: UpdatePostRequest, userPrincipal: UserPrincipal): PostResponse

    fun deletePost(postId: Long, userPrincipal: UserPrincipal)
}