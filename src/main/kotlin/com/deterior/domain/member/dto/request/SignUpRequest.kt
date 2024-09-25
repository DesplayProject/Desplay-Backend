package com.deterior.domain.member.dto.request

data class SignUpRequest (
    val username: String,
    var password: String,
    val email: String,
)