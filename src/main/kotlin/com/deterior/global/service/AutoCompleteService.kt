package com.deterior.global.service

import com.deterior.global.dto.AutoCompleteGetDto
import com.deterior.global.dto.AutoCompleteGetResponse
import com.deterior.global.repository.AutoCompleteRedisDao
import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AutoCompleteService @Autowired constructor(
    private val applicationProperties: ApplicationProperties,
    private val autoCompleteRedisDao: AutoCompleteRedisDao
) {
    fun getAutoComplete(keyword: String): AutoCompleteGetDto {
        val index = autoCompleteRedisDao.getIndex(keyword) ?: return AutoCompleteGetDto(emptyList())
        val limit = applicationProperties.autoComplete.limit
        val list = autoCompleteRedisDao.getRangeList(index)
        val autoCompletes = list.stream()
            .filter { it -> it.startsWith(keyword) && it.endsWith("*") }
            .map { it -> it.removeSuffix("*") }
            .limit(limit)
            .toList()
        return AutoCompleteGetDto(autoCompletes)
    }

    fun updateAutoComplete(input: String) {
        var str = ""
        val score = autoCompleteRedisDao.getScore(input)
        if (score == null) {
            for (s in input) {
                str += s
                if (str.length == input.length) str += "*"
                autoCompleteRedisDao.saveData(str, 0.0)
            }
        } else {
            autoCompleteRedisDao.saveData("$input*", score-1)
        }
    }
}