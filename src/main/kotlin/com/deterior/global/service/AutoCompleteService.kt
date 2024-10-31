package com.deterior.global.service

import com.deterior.global.dto.AutoCompleteDto
import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class AutoCompleteService @Autowired constructor(
    private val applicationProperties: ApplicationProperties,
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun getAutoComplete(keyword: String): AutoCompleteDto {
        val zSetOperations = redisTemplate.opsForZSet()
        var autoCompletes = emptyList<String>()
        val limit = applicationProperties.autoComplete.limit

        zSetOperations.rank("autoComplete", keyword)?.let {
            val list = zSetOperations.range("autoComplete", it, it + 1000) as Set<String>
            autoCompletes = list.stream()
                .filter { it -> it.startsWith(keyword) && it.endsWith("*") }
                .map { it -> it.removeSuffix("*") }
                .limit(limit)
                .toList()
        }
        return AutoCompleteDto(autoCompletes)
    }
}