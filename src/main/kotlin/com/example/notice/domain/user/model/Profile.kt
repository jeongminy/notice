package com.example.notice.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Profile (

    @Column(name = "nickname", nullable = false)
    val nickname: String

)