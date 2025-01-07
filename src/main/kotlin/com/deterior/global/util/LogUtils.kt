package com.deterior.global.util

import com.deterior.logger
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.util.*
import kotlin.collections.HashMap

@Component
class LogUtils {

    private val log = logger()

    fun printLog(request: ContentCachingRequestWrapper,
                 response: ContentCachingResponseWrapper,
                 time: Long,
                 id: String
    ) {
        val params = HashMap<String, String>()
        for (v in request.parameterMap) {
            params[v.key] = v.value.toString()
        }
        val message = """
            
        >-------------------| [${id}] |-------------------<
        [METHOD] ${request.method}
        [URL] ${request.requestURI}
        [CLIENT IP] ${request.remoteAddr}
            
        [REQUEST HEADERS] ${request.headerNames}
        [REQUEST PARAMS] 
        ${params}
        [REQUEST BODY] 
        ${request.contentAsByteArray.toString(Charsets.UTF_8)}
            
        [RESPONSE HEADERS] ${response.headerNames}
        [RESPONSE BODY] 
        ${response.contentAsByteArray.toString(Charsets.UTF_8)}
            
        [PROCESSING TIME] ${time}ms
        """.trimIndent()
        log.info(message)
    }
}