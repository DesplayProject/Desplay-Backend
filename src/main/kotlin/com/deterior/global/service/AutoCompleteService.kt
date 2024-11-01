package com.deterior.global.service

import com.deterior.global.dto.AutoCompleteDto
import com.deterior.global.repository.AutoCompleteRedisDao
import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class AutoCompleteService @Autowired constructor(
    private val applicationProperties: ApplicationProperties,
    private val autoCompleteRedisDao: AutoCompleteRedisDao
) {
    fun getAutoComplete(keyword: String): AutoCompleteDto {
        val index = autoCompleteRedisDao.getIndex(keyword) ?: return AutoCompleteDto(emptyList())
        val limit = applicationProperties.autoComplete.limit
        val list = autoCompleteRedisDao.getRangeList(index)
        val autoCompletes = list.stream()
            .filter { it -> it.startsWith(keyword) && it.endsWith("*") }
            .map { it -> it.removeSuffix("*") }
            .limit(limit)
            .toList()
        return AutoCompleteDto(autoCompletes)
    }

    fun updateAutoComplete(input: String) {
        var str = ""
        val score = autoCompleteRedisDao.getScore(input)
        if (score == null) {
            for (s in input) {
                str+=s
                if (str.length == input.length) str+="*"
                autoCompleteRedisDao.saveData(str, 0.0)
            }
        } else {
            autoCompleteRedisDao.saveData(input, score-1)
        }
    }
}