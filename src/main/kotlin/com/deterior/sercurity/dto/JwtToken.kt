package com.deterior.sercurity.dto

data class JwtToken (
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)