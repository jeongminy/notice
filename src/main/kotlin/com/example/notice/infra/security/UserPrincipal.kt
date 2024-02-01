package com.example.notice.infra.security

import com.example.notice.domain.user.model.Role
import com.example.notice.domain.user.model.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val email: String,
    val authorities: Collection<GrantedAuthority>
){
    constructor(id: Long, email: String, roles: Set<String>): this(
        id,
        email,
        roles.map {SimpleGrantedAuthority("ROLE_$it")}
    )
}

