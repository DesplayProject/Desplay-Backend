package com.deterior.global.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 재발급 DTO")
data class ReissueTokenRequest(

    @Schema(description = "Refresh Token", required = true)
    val refreshToken: String
)