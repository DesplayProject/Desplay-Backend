package com.deterior.global.util

import com.deterior.global.Log
import com.deterior.global.repository.LogRepository
import com.deterior.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.util.*
import kotlin.collections.HashMap

@Component
class LogUtils @Autowired constructor(
    private val logRepository: LogRepository
) {
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
        val log = Log(
            uuid = uuid,
            method = request.method,
            url = request.requestURI,
            clientIp = request.remoteAddr,
            params = params,
            requestBody = formatRequest(request),
            responseBody = formatResponse(response),
            processingTime = time,
        )
        //logRepository.save(log)
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