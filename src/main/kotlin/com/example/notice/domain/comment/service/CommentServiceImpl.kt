package com.example.notice.domain.comment.service

import com.example.notice.domain.comment.dto.request.AddCommentRequest
import com.example.notice.domain.comment.dto.request.UpdateCommentRequest
import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.comment.model.CommentEntity
import com.example.notice.domain.comment.model.toResponse
import com.example.notice.domain.comment.repository.CommentRepository
import com.example.notice.exception.RuntimeException.InvalidCredentialException
import com.example.notice.exception.RuntimeException.ModelNotFoundException
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.model.UserEntity
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

    override fun getComments(): List<CommentResponse> {
        return commentRepository.findAll().map { it.toResponse() }
    }

    override fun addComment(
        postId: Long,
        request: AddCommentRequest,
        userPrincipal: UserPrincipal,
    ): CommentResponse {
        val post: PostEntity = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user:UserEntity = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)
        val comment = CommentEntity(
            post = post,
            user = user,
            comment = request.comment
        )
        return commentRepository.save(comment).toResponse()
    }

    override fun updateComment(
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequest,
        userPrincipal: UserPrincipal,
    ): CommentResponse {
        val post: PostEntity = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user:UserEntity = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)
        val comment: CommentEntity = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("CommentEntity", commentId)

        //ADMIN이거나 본인인 경우에만 삭제 가능하도록 확인
        if ((userPrincipal.id != comment.user.id) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
            throw InvalidCredentialException("본인의 글이 아니므로 권한이 없습니다.")

        comment.comment = request.comment

        return comment.toResponse()
    }

    override fun deleteComment(postId: Long, commentId: Long, userPrincipal: UserPrincipal) {
        val post: PostEntity = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user:UserEntity = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)
        val comment: CommentEntity = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("CommentEntity", commentId)

        //ADMIN이거나 본인인 경우에만 삭제 가능하도록 확인
        if ((userPrincipal.id != comment.user.id) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
            throw InvalidCredentialException("본인의 글이 아니므로 권한이 없습니다.")

        commentRepository.delete(comment)
    }
}