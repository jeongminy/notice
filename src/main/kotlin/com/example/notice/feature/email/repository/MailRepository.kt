package com.example.notice.feature.email.repository

import com.example.notice.feature.email.model.MailEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository: JpaRepository<MailEntity, Long> {
}