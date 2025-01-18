package com.deterior.global.util

import com.deterior.global.log.LogDto
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import kotlin.collections.HashMap

@Component
class LogUtils {
    fun saveLog(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
        time: Long,
        uuid: String
    ) {
        val params = HashMap<String, String>()
        for (v in request.parameterMap) {
            params[v.key] = v.value.toString()
        }
        val logDto = LogDto(
            uuid = uuid,
            method = request.method,
            url = request.requestURI,
            clientIp = request.remoteAddr,
            params = params,
            requestBody = formatRequest(request),
            responseBody = formatResponse(response),
            processingTime = time,
        )
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