package com.example.notice.domain.user.model

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class UserEntity (

    @Column (name = "email")
    val email: String,

    @Column (name = "password")
    val password: String,

    @Column (name = "nickname")
    val nickname: String,

    @Column (name = "role")
    val role: RoleEntity

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}