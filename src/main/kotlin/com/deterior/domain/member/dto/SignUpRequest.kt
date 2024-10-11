package com.deterior.domain.member.dto

data class SignUpRequest (
    val username: String,
    var password: String,
    val email: String,
)