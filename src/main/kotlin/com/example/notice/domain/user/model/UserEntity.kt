package com.example.notice.domain.user.model

import com.example.notice.domain.user.dto.response.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class UserEntity (

    @Column (name = "email", nullable = false)
    val email: String,

    @Column (name = "password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column (name = "role", nullable = false)
    val role: Role,

    @Embedded
    var profile: Profile

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun UserEntity.toResponse(): UserResponse{
    return UserResponse(
        id = id!!,
        email = email,
        role = this.role,
        nickname = profile.nickname
    )
}