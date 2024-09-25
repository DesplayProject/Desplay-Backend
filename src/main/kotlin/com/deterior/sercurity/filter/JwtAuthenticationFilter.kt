package com.deterior.sercurity.filter

import com.deterior.global.util.LoggerCreator
import com.deterior.sercurity.provider.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.lang.NullPointerException

class JwtAuthenticationFilter (
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {

    companion object : LoggerCreator()

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        try {
            val token: String? = resolveToken(request as HttpServletRequest)
            if(token != null && jwtTokenProvider.validateToken(token)) {
                val authentication: Authentication = jwtTokenProvider.authenticate(token)
                SecurityContextHolder.getContextHolderStrategy().context.authentication = authentication
            }
        } catch (exception: NullPointerException) {
            log.warn("인증되지 않은 사용자의 접속 요청.")
        }
        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken: String = request.getHeader("Authorization")
        if(bearerToken.contains(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7)
        }
        return null
    }
}