package com.example.notice.domain.email.repository

import com.example.notice.domain.email.model.MailEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository: JpaRepository<MailEntity, Long> {
}