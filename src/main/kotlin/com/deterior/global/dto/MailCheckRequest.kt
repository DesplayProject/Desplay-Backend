package com.deterior.global.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "인증 메일 확인 DTO")
data class MailCheckRequest(

    @Schema(description = "받는 이메일")
    val receiverMail: String,

    @Schema(description = "인증 번호")
    val authNumber: Int
)