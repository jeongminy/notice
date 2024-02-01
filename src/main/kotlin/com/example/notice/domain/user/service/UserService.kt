package com.example.notice.domain.user.service

import com.example.notice.domain.user.dto.request.JoinRequest
import com.example.notice.domain.user.dto.response.UserResponse

interface UserService {

    fun join(reqeust: JoinRequest): UserResponse
}