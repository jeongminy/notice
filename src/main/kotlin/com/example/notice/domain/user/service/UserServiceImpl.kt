package com.example.notice.domain.user.service

import com.example.notice.domain.exception.DuplicatedPropertyException
import com.example.notice.domain.exception.InvalidCredentialException
import com.example.notice.domain.exception.ModelNotFoundException
import com.example.notice.domain.user.dto.request.JoinRequest
import com.example.notice.domain.user.dto.request.LoginRequest
import com.example.notice.domain.user.dto.response.LoginResponse
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

    override fun login(request: LoginRequest): LoginResponse {

        //확인사항1: 이메일 존재 여부 확인
        val user = userRepository.findByEmail(request.email)
            ?: throw ModelNotFoundException("User", null)

        //확인사항2: 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.password, user.password))
            throw InvalidCredentialException("비밀번호가 일치하지 않습니다.")

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.name
            )
        )
    }

    override fun join(request: JoinRequest): UserResponse {

        //확인사항1: 이메일 중복 체크
        if (userRepository.existsUserEntityByEmail(request.email))
            throw DuplicatedPropertyException("이메일", request.email)

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