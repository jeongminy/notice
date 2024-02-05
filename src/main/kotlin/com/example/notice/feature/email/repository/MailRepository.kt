package com.example.notice.infra.email.repository

import com.example.notice.infra.email.model.MailEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository: JpaRepository<MailEntity, Long> {
}