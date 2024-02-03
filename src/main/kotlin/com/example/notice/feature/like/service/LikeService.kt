package com.example.notice.feature.like.service

import com.example.notice.infra.security.UserPrincipal

interface LikeService {

    fun like(userPrincipal: UserPrincipal, postId: Long): Boolean

    fun countLikes(feedId: Long): Int

    fun isLiked(userId: Long, feedId: Long): Boolean
}