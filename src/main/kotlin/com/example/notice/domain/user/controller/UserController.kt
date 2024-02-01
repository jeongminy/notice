package com.example.notice.domain.user.controller

import com.example.notice.domain.user.dto.request.JoinRequest
import com.example.notice.domain.user.dto.request.LoginRequest
import com.example.notice.domain.user.dto.response.LoginResponse
import com.example.notice.domain.user.dto.response.UserResponse
import com.example.notice.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user", description = "유저")
@RequestMapping("/api/users")
@RestController
class UserController(
    private val userService: UserService,
) {

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest,
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequest))
    }

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    fun join(
        @Valid @RequestBody signUpRequest: JoinRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.join(signUpRequest))
    }
}