package com.example.notice.domain.comment.service

import com.example.notice.domain.comment.dto.request.AddCommentRequest
import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.comment.model.CommentEntity
import com.example.notice.domain.comment.model.toResponse

import com.example.notice.domain.comment.repository.CommentRepository
import com.example.notice.domain.exception.ModelNotFoundException
import com.example.notice.domain.post.repository.PostRepository

import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
): CommentService {

    override fun addComment(
        postId: Long,
        request: AddCommentRequest,
        userPrincipal: UserPrincipal
    ): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)
        val comment = CommentEntity(
            post = post,
            user = user,
            comment = request.comment
        )
        return commentRepository.save(comment).toResponse()
    }

}