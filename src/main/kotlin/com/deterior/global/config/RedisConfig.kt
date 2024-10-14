package com.deterior.global.config

import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Configuration
class RedisConfig @Autowired constructor(
  val applicationProperties: ApplicationProperties
) {
    val host = applicationProperties.data.redis.host
    val port = applicationProperties.data.redis.port

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, JvmType.Object> {
        val redisTemplate: RedisTemplate<String, JvmType.Object> = RedisTemplate<String, JvmType.Object>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.connectionFactory = redisConnectionFactory()
        return redisTemplate
    }
}