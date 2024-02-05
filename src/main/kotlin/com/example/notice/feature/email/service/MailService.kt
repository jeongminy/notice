package com.example.notice.infra.email.service

import com.example.notice.infra.email.dto.MailResponse

interface MailService {

    fun sendMail(email: String): MailResponse
}