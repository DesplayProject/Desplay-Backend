package com.deterior.global.dto

import com.deterior.global.RefreshToken
import org.springframework.security.core.Authentication

data class JwtToken (
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
) {
    fun toRefreshToken(authentication: Authentication): RefreshToken = RefreshToken(
        key = authentication.name,
        value = this.refreshToken
    )
}