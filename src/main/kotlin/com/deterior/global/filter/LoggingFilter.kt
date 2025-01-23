package com.deterior.global.filter

import com.deterior.global.log.StreamCachingRequestWrapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.util.*

@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
class LoggingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestWrapper = StreamCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)
        val uuid = UUID.randomUUID().toString()
        MDC.put("requestId", uuid)

        requestWrapper.setAttribute("requestId", uuid)
        filterChain.doFilter(requestWrapper, responseWrapper)
        responseWrapper.copyBodyToResponse()

        MDC.remove("requestId")
    }
}