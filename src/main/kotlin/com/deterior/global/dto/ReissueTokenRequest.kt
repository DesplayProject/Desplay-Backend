package com.deterior.global.dto

data class ReissueTokenRequest(
    val accessToken: String,
    val refreshToken: String
)