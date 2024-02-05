package com.example.notice.feature.email.service

import com.example.notice.feature.email.dto.MailResponse

interface MailService {

    fun sendMail(email: String): MailResponse
}