package com.example.notice.domain.comment.repository

import com.example.notice.domain.comment.model.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<CommentEntity, Long> {

    fun findAllByPostId(postId: Long): List<CommentEntity?>

}