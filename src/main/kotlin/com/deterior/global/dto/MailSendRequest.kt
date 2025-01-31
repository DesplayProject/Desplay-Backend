package com.deterior.global.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "인증 메일 DTO")
data class MailSendRequest(

    @Schema(description = "받는 이메일", required = true)
    val receiverMail: String,
)