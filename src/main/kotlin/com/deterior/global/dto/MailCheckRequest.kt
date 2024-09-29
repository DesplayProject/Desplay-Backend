package com.deterior.global.dto

data class MailCheckRequest(
    val receiverMail: String,
    val authNumber: Int
)