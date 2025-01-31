package com.deterior.global.controller

import com.deterior.global.dto.MailCheckRequest
import com.deterior.global.dto.MailCheckResponse
import com.deterior.global.dto.MailSendRequest
import com.deterior.global.dto.MailSendResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "메일 API")
interface MailControllerSwagger {

    @Operation(summary = "인증 메일 보내기", description = "랜덤한 문자열을 이메일로 보냅니다.")
    fun sendAuthMail(mailSendRequest: MailSendRequest): ResponseEntity<MailSendResponse>

    @Operation(summary = "메일 인증 확인", description = "메일로 보낸 랜덤한 문자열을 확인합니다.")
    fun checkAuthMail(mailCheckRequest: MailCheckRequest): ResponseEntity<MailCheckResponse>
}