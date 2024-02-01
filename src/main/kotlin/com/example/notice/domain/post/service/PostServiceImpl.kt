package com.example.notice.domain.post.service

import com.example.notice.domain.exception.ModelNotFoundException
import com.example.notice.domain.post.dto.AddPostRequest
import com.example.notice.domain.post.dto.PostResponse
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.model.toResponse
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
): PostService {

    override fun getPosts(): List<PostResponse> {
        return postRepository.findAll().map { it.toResponse() }
    }

    override  fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("PostEntity", postId)
        return post.toResponse()
    }

    override fun addPost(request: AddPostRequest, userPrincipal: UserPrincipal): PostResponse {
        val user = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)
        return postRepository.save(
            PostEntity(
                title = request.title,
                description = request.description,
                status = PostStatus.UNCOMPLETE,
                postImageUrl = request.postImageUrl,
                user = user
            )
        ).toResponse()

    }
}