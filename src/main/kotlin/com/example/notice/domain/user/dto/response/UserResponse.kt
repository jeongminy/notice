package com.example.notice.domain.user.dto.response

import com.example.notice.domain.user.model.Role

data class UserResponse (
    val id: Long,
    val email: String,
    val role: Role,
    val nickname: String,
)