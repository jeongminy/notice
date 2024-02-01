package com.example.notice.domain.user.model

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class UserEntity (

    @Column (name = "email", nullable = false)
    val email: String,

    @Column (name = "password", nullable = false)
    val password: String,

    @Column (name = "role", nullable = false)
    val role: Role,

    @Embedded
    var profile: Profile

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}