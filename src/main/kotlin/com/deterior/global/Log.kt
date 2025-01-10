package com.deterior.global

import com.deterior.logger
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Document(collection = "log")
data class Log(
    val uuid: String,
    val method: String,
    val url: String,
    val clientIp: String,
    val params: HashMap<String, String>,
    val requestBody: String,
    val responseBody: String,
    val processingTime: Long
) {
    @Id
    var id: String? = null
        private set

    private val log = logger()

    fun printLog() {
        val message = """
            
        >-------------------| [${uuid}] |-------------------<
        [METHOD] ${method}
        [URL] ${url}
        [CLIENT IP] ${clientIp}
            
        [REQUEST PARAMS] 
        ${params}
        [REQUEST BODY] 
        ${requestBody}
            
        [RESPONSE BODY] 
        ${responseBody}
            
        [PROCESSING TIME] ${processingTime}ms
        """.trimIndent()
        log.info(message)
    }
}