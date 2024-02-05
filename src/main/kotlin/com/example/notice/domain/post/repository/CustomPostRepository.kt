package com.example.notice.domain.post.repository

import com.example.notice.domain.post.model.PostEntity
import com.example.notice.domain.post.model.PostStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface CustomPostRepository {

    fun searchPostListByTitle(title: String): List<PostEntity>

    fun findByPageableAndStatus(pageable: Pageable, postStatus: PostStatus?): Page<PostEntity>

    fun searchPostListByCreatedAt(): List<PostEntity>

}