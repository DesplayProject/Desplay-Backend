package com.deterior.sercurity.dto

import com.deterior.sercurity.RefreshToken
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