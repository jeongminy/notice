package com.example.notice.domain.email.controller

import com.example.notice.domain.email.dto.MailResponse
import com.example.notice.domain.email.service.MailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping

import org.springframework.web.bind.annotation.*

@Tag(name = "mail", description = "이메일")
@RestController
class MailController(private val mailService: MailService) {

    @Operation(summary = "이메일 인증")
    @PostMapping("/api/email")
    fun sendMail(
        @RequestParam email : String
    ): ResponseEntity<MailResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mailService.sendMail(email))
    }
}