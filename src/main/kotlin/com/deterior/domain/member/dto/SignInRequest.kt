package com.deterior.domain.member.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 DTO")
data class SignInRequest(

    @Schema(description = "아이디", required = true)
    val username: String,

    @Schema(description = "비밀번호", required = true)
    val password: String
)