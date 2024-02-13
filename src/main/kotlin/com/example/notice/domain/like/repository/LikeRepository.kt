package com.example.notice.domain.like.repository

import com.example.notice.domain.like.model.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<LikeEntity, Long> {

    fun existsByUserIdAndPostId(userId: Long, postId: Long): Boolean
    fun deleteByUserIdAndPostId(userId: Long, postId: Long): Int
    fun countByPostId(postId: Long): Int
}