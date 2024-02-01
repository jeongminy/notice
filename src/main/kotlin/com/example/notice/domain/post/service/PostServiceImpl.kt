package com.example.notice.domain.post.service


import com.example.notice.domain.exception.ModelNotFoundException
import com.example.notice.domain.post.dto.request.AddPostRequest
import com.example.notice.domain.post.dto.request.UpdatePostRequest
import com.example.notice.domain.post.dto.response.PostResponse
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.model.toResponse
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


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

    override fun updatePost(postId: Long, request: UpdatePostRequest, userPrincipal: UserPrincipal): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("PostEntity", postId)

            post.title = request.title
            post.description = request.description
            post.status = request.status
            post.postImageUrl = request.postImageUrl
            post.updatedAt = LocalDateTime.now()

        return post.toResponse()
    }

    override fun deletePost(postId: Long, userPrincipal: UserPrincipal) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("PostEntity", postId)

        postRepository.delete(post)
    }
}