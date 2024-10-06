package com.deterior.global.controller

import com.deterior.global.dto.*
import com.deterior.global.repository.MailCheckDao
import com.deterior.global.service.MailService
import com.deterior.global.service.MailType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/mail")
class MailController @Autowired constructor(
    private val mailService: MailService,
    private val mailCheckDao: MailCheckDao
) {

    @PostMapping("/auth-send")
    fun sendAuthMail(
        @RequestBody mailSendRequest: MailSendRequest
    ): ResponseEntity<MailSendResponse> {
        val mailDto = MailDto(
            receiverMail = mailSendRequest.receiverMail,
            type = MailType.EMAIL_AUTH
        )
        val mail = mailSendRequest.receiverMail
        mailService.sendMail(mailDto)
        return ResponseEntity.ok(MailSendResponse(mail))
    }

    @PostMapping("/check")
    fun checkAuthMail(
        @RequestBody mailCheckRequest: MailCheckRequest
    ): ResponseEntity<MailCheckResponse> {
        val mail = mailCheckRequest.receiverMail
        val authNUm = mailCheckRequest.authNumber
        mailCheckDao.authNumberCheck(mail, authNUm)
        return ResponseEntity.ok(MailCheckResponse(mail))
    }
}