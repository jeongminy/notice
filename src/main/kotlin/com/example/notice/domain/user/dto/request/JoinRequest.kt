package com.example.notice.domain.user.dto.request

import com.example.notice.domain.user.model.Role

data class JoinRequest(
    val email: String,
    val password: String,
    val role: Role,
    val nickname: String,
)