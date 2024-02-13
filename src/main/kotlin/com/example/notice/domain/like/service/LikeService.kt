package com.example.notice.domain.like.service

import com.example.notice.infra.security.UserPrincipal

interface LikeService {

    fun like(userPrincipal: UserPrincipal, postId: Long): Boolean

    fun countLikes(postId: Long): Int

    fun isLiked(userId: Long, postId: Long): Boolean
}