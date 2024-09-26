package com.deterior.global.controller

import com.deterior.global.dto.MailSendRequest
import com.deterior.global.dto.MailSendResponse
import com.deterior.global.service.MailService
import com.deterior.global.service.MailType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.util.concurrent.ConcurrentHashMap

@Controller
@RequestMapping("/mail")
class MailController @Autowired constructor(
    private val mailService: MailService
) {
    val authNumbers = ConcurrentHashMap<String, Int>()

    @PostMapping("/auth-send")
    fun sendAuthMail(
        @RequestBody mailSendRequest: MailSendRequest
    ): ResponseEntity<MailSendResponse> {
        val mail = mailSendRequest.receiverMail
        val authNum = mailService.sendMail(MailType.EMAIL_AUTH, mail)
        authNumbers[mail] = authNum
        return ResponseEntity.ok(MailSendResponse(mail))
    }
}