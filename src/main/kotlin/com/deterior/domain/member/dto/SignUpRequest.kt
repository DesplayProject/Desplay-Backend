package com.deterior.domain.member.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원가입 DTO")
data class SignUpRequest (

    @Schema(description = "아이디", required = true)
    val username: String,

    @Schema(description = "비밀번호", required = true)
    var password: String,

    @Schema(description = "이메일", required = true)
    val email: String,
)