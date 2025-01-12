package com.deterior.global.filter

import com.deterior.global.log.CachedBodyHttpServletRequest
import com.deterior.global.util.LogUtils
import com.deterior.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
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
        val requestWrapper = ContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)
        val uuid = UUID.randomUUID().toString()
        MDC.put("requestId", uuid)

        requestWrapper.setAttribute("requestId", uuid)
        filterChain.doFilter(requestWrapper, responseWrapper)
        responseWrapper.copyBodyToResponse()

        MDC.remove("requestId")
    }
}