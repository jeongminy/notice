package com.example.notice.domain.user.repository

import com.example.notice.domain.user.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long>{

    fun findByEmail(email: String): UserEntity?

    fun existsUserEntityByEmail(email: String): Boolean

    fun existsUserEntityByProfileNickname(nickname: String): Boolean
}