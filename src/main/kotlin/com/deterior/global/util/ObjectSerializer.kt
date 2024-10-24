package com.deterior.global.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ObjectSerializer @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun <T> saveData(key: String, data: T, hour: Long) {
        val mapper = createObjectMapper()
        val value = mapper.writeValueAsString(data)
        redisTemplate.opsForValue().set(key, value, hour, TimeUnit.HOURS)
    }

    fun <T> getData(key: String, classType: Class<T>): T? {
        val value = redisTemplate.opsForValue().get(key) ?: return null
        val mapper = createObjectMapper()
        return mapper.readValue(value, classType)
    }

    fun createObjectMapper(): ObjectMapper = ObjectMapper()
        .registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build())
}