package com.deterior.global.service

import com.deterior.global.util.ApplicationProperties
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.security.SecureRandom
import kotlin.math.abs

//cozq njji eles osma
@Service
class MailService @Autowired constructor(
    val javaMailSender: JavaMailSender,
    val applicationProperties: ApplicationProperties
){
    fun sendMail(mailType: MailType, receiverMail: String): Int {
        val message = createMessageHeader(mailType, receiverMail)
        val authNum = createNumber()
        when (mailType) {
            MailType.EMAIL_AUTH -> {
                message.subject = mailType.title
                message.setText(mailType.content + authNum, "UTF-8", "html")
                javaMailSender.send(message)
            }
        }
        return authNum
    }

    private fun createMessageHeader(mailType: MailType, receiverMail: String): MimeMessage {
        val message = javaMailSender.createMimeMessage()
        message.setFrom(applicationProperties.mail.username)
        message.setRecipients(MimeMessage.RecipientType.TO, receiverMail)
        return message
    }

    private fun createNumber() = abs(SecureRandom.getInstanceStrong().nextInt())
}