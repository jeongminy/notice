package com.example.notice.domain.email.model

import jakarta.persistence.*

@Entity
@Table(name = "mail")
class MailEntity(
    @Column(name="email", nullable = false)
    val email: String,

    @Column(name="code", nullable = false)
    var code: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}