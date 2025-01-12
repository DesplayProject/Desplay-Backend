package com.deterior.global.log

import com.deterior.logger
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
@Aspect
class LoggerAspect {

    private val objectMapper = ObjectMapper()
    private val log = logger()

    @Pointcut("execution(* com.deterior..controller..*(..))")
    fun pointCut() {}

    @Around("pointCut()")
    fun aopLogging(joinPoint: ProceedingJoinPoint): Any {
        //현재 쓰레드의 request
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val uuid = request.getAttribute("requestId") as String

        val start = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val end = System.currentTimeMillis()
        val processingTime = end - start

        val response = when(result) {
            is ResponseEntity<*> -> result.body
            else -> ""
        }
        val logDto = LogDto(
            uuid = uuid,
            method = request.method,
            url = request.requestURI,
            clientIp = request.remoteAddr,
            params = getParams(request),
            requestBody = objectMapper.readTree(request.inputStream.readBytes()),
            responseBody = objectMapper.writeValueAsString(response),
            processingTime = processingTime,
        )
        log.info(objectMapper.writeValueAsString(logDto))
        return result
    }

    private fun getParams(request: HttpServletRequest): HashMap<String, String> {
        val params = HashMap<String, String>()
        for (v in request.parameterMap) {
            params[v.key] = v.value.toString()
        }
        return params
    }

    private fun formatRequest(request: ContentCachingRequestWrapper): String =
        request.contentAsByteArray.toString(Charsets.UTF_8)
            .replace(Regex("\\s+"), "")
            .replace(Regex("\\{"), "\\{\n\t")
            .replace(Regex("\\}"), "\n\t\\}")
            .replace(Regex(","), ",\n\t")

    private fun formatResponse(response: ContentCachingResponseWrapper) =
        response.contentAsByteArray.toString(Charsets.UTF_8)
            .replace(Regex("\\s+"), "")
            .replace(Regex("\\{"), "\\{\n\t")
            .replace(Regex("\\}"), "\n\t\\}")
            .replace(Regex(","), ",\n\t")
}