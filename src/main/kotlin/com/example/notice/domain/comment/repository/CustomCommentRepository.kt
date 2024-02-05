package com.example.notice.domain.comment.repository

import com.example.notice.domain.comment.dto.response.CommentResponse
import com.example.notice.domain.comment.model.CommentEntity
import com.example.notice.domain.post.model.PostEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomCommentRepository {

    fun findByPageable(pageable: Pageable): Page<CommentEntity>
}