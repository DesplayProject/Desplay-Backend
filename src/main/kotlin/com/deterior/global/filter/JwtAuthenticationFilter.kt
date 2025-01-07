package com.deterior.global.filter

import com.deterior.global.util.LoggerCreator
import com.deterior.global.service.JwtProvider
import com.deterior.global.util.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.lang.NullPointerException

class JwtAuthenticationFilter (
    private val jwtProvider: JwtProvider,
    private val jwtUtils: JwtUtils,
) : OncePerRequestFilter() {

    companion object : LoggerCreator()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token: String? = jwtUtils.resolveToken(request)
            if(token != null && jwtUtils.validateToken(token)) {
                val authentication: Authentication = jwtProvider.authenticate(token)
                SecurityContextHolder.getContextHolderStrategy().context.authentication = authentication
            }
        } catch (exception: NullPointerException) {
            log.info("인증되지 않은 사용자의 접속 요청.")
        }
        filterChain.doFilter(request, response)
    }
}