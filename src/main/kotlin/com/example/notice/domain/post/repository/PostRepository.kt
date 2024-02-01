package com.example.notice.domain.post.repository

import com.example.notice.domain.post.model.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<PostEntity, Long>