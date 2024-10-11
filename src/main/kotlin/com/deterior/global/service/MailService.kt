package com.deterior.global.service

import com.deterior.global.dto.MailDto
import com.deterior.global.repository.MailCheckDao
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
    private val javaMailSender: JavaMailSender,
    private val mailCheckDao: MailCheckDao,
    private val applicationProperties: ApplicationProperties
){
    fun sendMail(mailDto: MailDto) {
        val mailType = mailDto.type
        val receiverMail = mailDto.receiverMail
        val message = createMessageHeader(receiverMail)
        when (mailType) {
            MailType.EMAIL_AUTH -> {
                val authNum = mailCheckDao.saveAuthNumber(receiverMail)
                message.subject = mailType.title
                message.setText(mailType.content + authNum, "UTF-8", "html")
                javaMailSender.send(message)
            }
        }
    }

    private fun createMessageHeader(receiverMail: String): MimeMessage {
        val message = javaMailSender.createMimeMessage()
        message.setFrom(applicationProperties.mail.username)
        message.setRecipients(MimeMessage.RecipientType.TO, receiverMail)
        return message
    }
}