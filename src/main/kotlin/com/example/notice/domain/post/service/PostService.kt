package com.example.notice.domain.post.service

import com.example.notice.domain.post.dto.request.AddPostRequest
import com.example.notice.domain.post.dto.request.UpdatePostRequest
import com.example.notice.domain.post.dto.response.PostByIdResponse
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface PostService {

    fun getPostList(): List<PostResponse>

    fun getPostById(postId: Long, userPrincipal: UserPrincipal): PostByIdResponse

    fun addPost(addPostRequest: AddPostRequest, userPrincipal: UserPrincipal): PostResponse

    fun updatePost(postId: Long, updatePostRequest: UpdatePostRequest, userPrincipal: UserPrincipal): PostResponse

    fun deletePost(postId: Long, userPrincipal: UserPrincipal)

    fun getPostListPaginated(pageable: Pageable, status: String?): Page<PostResponse>

    fun getPostListByTitle(title: String): List<PostResponse>

    fun getPostListByCreatedAt(): List<PostResponse>
}