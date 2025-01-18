package com.deterior.global.log

import com.deterior.logger

data class LogDto(
    val uuid: String,
    val method: String,
    val url: String,
    val clientIp: String,
    val params: HashMap<String, String>,
    val requestBody: Any,
    val responseBody: Any,
    val processingTime: Long
) {
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