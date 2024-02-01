package com.example.notice.domain.comment.service

import com.example.notice.domain.comment.dto.request.AddCommentRequest
import com.example.notice.domain.comment.dto.request.UpdateCommentRequest
import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.infra.security.UserPrincipal

interface CommentService {

    fun addComment(postId: Long, addCommentRequest: AddCommentRequest , userPrincipal: UserPrincipal): CommentResponse

    fun updateComment(postId: Long, commentId: Long, updateCommentRequest: UpdateCommentRequest, userPrincipal: UserPrincipal): CommentResponse

}