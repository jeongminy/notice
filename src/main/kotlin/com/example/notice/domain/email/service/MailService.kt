package com.example.notice.domain.email.service

import com.example.notice.domain.email.dto.MailResponse

interface MailService {

    fun sendMail(email: String): MailResponse
}