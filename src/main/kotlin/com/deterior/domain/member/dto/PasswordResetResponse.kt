package com.deterior.domain.member.dto

data class PasswordResetResponse(
    val username: String,
    val password: String
)