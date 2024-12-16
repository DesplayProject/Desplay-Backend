package com.deterior.domain.member.dto

data class PasswordResetRequest(
    val email: String,
    val newPassword: String,
)