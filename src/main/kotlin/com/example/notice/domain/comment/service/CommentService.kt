package com.example.notice.domain.comment.service

import com.example.notice.domain.comment.dto.request.AddCommentRequest
import com.example.notice.domain.comment.dto.request.UpdateCommentRequest
import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {

    fun getCommentList(): List<CommentResponse>

    fun addComment(postId: Long, addCommentRequest: AddCommentRequest , userPrincipal: UserPrincipal): CommentResponse

    fun updateComment(postId: Long, commentId: Long, updateCommentRequest: UpdateCommentRequest, userPrincipal: UserPrincipal): CommentResponse

    fun deleteComment(postId: Long, commentId: Long, userPrincipal: UserPrincipal)

    fun getCommentListPaginated(pageable: Pageable): Page<CommentResponse>

}