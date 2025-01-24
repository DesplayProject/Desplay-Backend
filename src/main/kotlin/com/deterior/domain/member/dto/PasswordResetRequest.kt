package com.deterior.domain.member.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "비밀번호 초기화 DTO")
data class PasswordResetRequest(

    @Schema(description = "이메일")
    val email: String,

    @Schema(description = "변경할 비밀번호")
    val newPassword: String,
)