package com.deterior.global.service

import com.deterior.global.exception.NoAuthorizationInTokenException
import com.deterior.global.util.JwtUtils
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Slf4j
@Component
class JwtProvider @Autowired constructor(
    private val jwtUtils: JwtUtils,
    private val jwtUserDetailsService: JwtUserDetailsService
) {
    fun authenticate(accessToken: String): Authentication {
        val claims = jwtUtils.parseClaims(accessToken)
        if(claims["auth"] == null) {
            throw NoAuthorizationInTokenException("권한 정보가 없는 토큰입니다.")
        }
        val authorities = claims["auth"]
            .toString().split(",")
            .map { SimpleGrantedAuthority(it) }
            .toList()
        val user = jwtUserDetailsService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(user, "", authorities)
    }
}