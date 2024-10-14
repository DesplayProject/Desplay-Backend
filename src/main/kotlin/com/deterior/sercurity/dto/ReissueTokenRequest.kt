package com.deterior.sercurity.dto

data class ReissueTokenRequest(
    val accessToken: String,
    val refreshToken: String
)