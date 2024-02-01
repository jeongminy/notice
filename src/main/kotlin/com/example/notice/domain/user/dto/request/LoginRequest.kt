package com.example.notice.domain.user.dto.request

data class LoginRequest (
    val email: String,
    val password: String,
)