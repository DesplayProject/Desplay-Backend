package com.deterior.global.util

import com.deterior.global.service.AutoCompleteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class InitRedisService @Autowired constructor (
    private val redisTemplate: RedisTemplate<String, String>,
    private val autoCompleteService: AutoCompleteService
) {
    fun init() {
        fillAutoCompleteString()
    }

    private fun fillAutoCompleteString() {
        val list = listOf("RTX 4070", "RTX 4070ti", "RTX 4080 super", "RTX 4090", "RTX 4090", "RTX 6974", "RTX 6974", "RTX 6974")
        val zSetOperations = redisTemplate.opsForZSet()
        for(string in list) {
            val score = zSetOperations.score("autoComplete", "$string*")
            var temp = ""
            if (score == null) {
                for (str in string) {
                    temp += str
                    if (temp.length == string.length) temp += "*"
                    zSetOperations.add("autoComplete", temp, 0.0)
                }
            } else {
                zSetOperations.add("autoComplete", "$temp*", score-1)
            }
        }
    }
}