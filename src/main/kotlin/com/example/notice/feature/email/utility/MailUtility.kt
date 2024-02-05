package com.example.notice.feature.email.utility

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import java.util.*

@Component
class MailUtility(
    val javaMailSender: JavaMailSender
) {
    fun getRandomString() : String {
        val length = 6
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")

//    fun getRandomString() : String {
//        val length = 6
//        val code = UUID.randomUUID().toString()
//        return UUID.substring(0,length)

    }

    //이메일 발송하기
    fun sendMail(email: String): String {

        val randomString = getRandomString()

        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)
        helper.setTo(email) // 수신자 email
        helper.setSubject("메일 제목")
        helper.setText("인증 코드 : $randomString")
        helper.setFrom("gks777777@gmail.com") //발송할 이메일
        javaMailSender.send(message)

        return randomString
    }
}