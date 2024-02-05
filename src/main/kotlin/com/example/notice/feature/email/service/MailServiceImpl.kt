package com.example.notice.feature.email.service

import com.example.notice.feature.email.dto.MailResponse
import com.example.notice.feature.email.model.MailEntity
import com.example.notice.feature.email.repository.MailRepository
import com.example.notice.feature.email.utility.MailUtility
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    private val mailRepository: MailRepository,
    private val mailUtility: MailUtility,
): MailService {

    override fun sendMail(email: String): MailResponse {

        val randomString = mailUtility.sendMail(email)

        mailRepository.save(
            MailEntity(
                email = email,
                code = randomString,
            )
        )

        return MailResponse(message = "메일 발송 완료")
    }
}

