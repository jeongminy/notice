package com.example.notice.domain.user.service

import com.example.notice.domain.user.dto.request.JoinRequest
import com.example.notice.domain.user.dto.response.UserResponse
import com.example.notice.domain.user.model.Profile
import com.example.notice.domain.user.model.UserEntity
import com.example.notice.domain.user.model.toResponse
import com.example.notice.domain.user.repository.UserRepository
import com.example.notice.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
):UserService {

    override fun join(request: JoinRequest): UserResponse {
        return userRepository.save(
            UserEntity(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                role = request.role,
                Profile(nickname = request.nickname),
            )
        ).toResponse()
    }
}