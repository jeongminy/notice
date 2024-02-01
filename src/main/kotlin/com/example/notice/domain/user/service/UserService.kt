package com.example.notice.domain.user.service

import com.example.notice.domain.user.dto.request.JoinRequest
import com.example.notice.domain.user.dto.request.LoginRequest
import com.example.notice.domain.user.dto.response.LoginResponse
import com.example.notice.domain.user.dto.response.UserResponse

interface UserService {

    fun join(request: JoinRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse
}