package com.deterior.global.dto.request

data class MailCheckRequest(
    val receiverMail: String,
    val authNumber: Int
)