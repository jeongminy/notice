package com.example.notice.domain.user.dto.request

import com.example.notice.domain.user.model.Role
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class JoinRequest(

    @field:NotBlank(message = "이메일을 입력해주세요.")
    @Schema(description = "이메일", example = "email@email.com")
    val email: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "비밀번호", example = "Password12!@")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "비밀번호는 최소 8자 이상, 숫자, 문자, 특수문자를 포함해야 합니다. 공백은 포함하지 않습니다.")
    val password: String,

    @field:NotBlank(message = "비밀번호확인을 입력해주세요.")
    @Schema(description = "비밀번호", example = "Password12!@")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "비밀번호는 최소 8자 이상, 숫자, 문자, 특수문자를 포함해야 합니다. 공백은 포함하지 않습니다.")
    val verifiedPassword: String,

    @field:NotBlank(message = "역할을 입력해주세요.")
    @Schema(description = "역할", example = "USER")
    val role: Role,

    @field:NotBlank(message = "닉네임을 입력해주세요.")
    @Schema(description = "닉네임", example = "Nickname")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]{3,}\$",
        message = "닉네임은 최소 3자 이상, 알파벳 대소문자(az, AZ), 숫자(0~9)로 구성해야 합니다. 공백은 포함하지 않습니다.")
    val nickname: String,
)