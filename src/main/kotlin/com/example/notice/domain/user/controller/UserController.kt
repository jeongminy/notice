package com.example.notice.domain.user.controller

import com.example.notice.domain.user.dto.request.JoinRequest
import com.example.notice.domain.user.dto.response.UserResponse
import com.example.notice.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/join")
    fun join(
        @RequestBody signUpRequest: JoinRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.join(signUpRequest))
    }
}