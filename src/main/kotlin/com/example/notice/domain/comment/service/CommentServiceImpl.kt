package com.example.notice.domain.comment.service

import com.example.notice.domain.comment.dto.request.AddCommentRequest
import com.example.notice.domain.comment.dto.request.UpdateCommentRequest
import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.comment.model.CommentEntity
import com.example.notice.domain.comment.model.toResponse
import com.example.notice.domain.comment.repository.CommentRepository
import com.example.notice.exception.runtimeException.InvalidCredentialException
import com.example.notice.exception.runtimeException.ModelNotFoundException
import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import com.example.notice.domain.post.model.toResponse
import com.example.notice.domain.post.repository.PostRepository
import com.example.notice.domain.user.model.UserEntity
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommentServiceImpl(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
): CommentService {

    override fun getCommentList(): List<CommentResponse> {
        return commentRepository.findAll().map { it.toResponse() }
    }

    @Transactional
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

    @Transactional
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
        comment.updatedAt = LocalDateTime.now()

        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long, userPrincipal: UserPrincipal) {
        val post: PostEntity = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user:UserEntity = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("UserEntity", userPrincipal.id)
        val comment: CommentEntity = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("CommentEntity", commentId)

        //ADMIN이거나 본인인 경우에만 삭제 가능하도록 확인
        if ((userPrincipal.id != comment.user.id) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
            throw InvalidCredentialException("본인의 글이 아니므로 권한이 없습니다.")

        commentRepository.delete(comment)
    }

    //댓글 목록 페이지네이션 - 페이징 + 커스텀 정렬 (id, nickname, comment, createdAt)
    override fun getCommentListPaginated(pageable: Pageable): Page<CommentResponse> {
        return commentRepository.findByPageable(pageable).map { it.toResponse() }
    }
}