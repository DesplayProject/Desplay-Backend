package com.deterior.global.repository

import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class AutoCompleteRedisDao @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, String>
) {
    private val zSetOperations = redisTemplate.opsForZSet()

    fun getIndex(input: String): Long? = zSetOperations.rank("autoComplete", input)

    fun getRangeList(index: Long): Set<String> = zSetOperations.range("autoComplete", index, index + 1000) as Set<String>

    fun getScore(input: String): Double? = zSetOperations.score("autoComplete", "$input*")

    fun saveData(input: String, score: Double) = zSetOperations.add("autoComplete", input, score)
}