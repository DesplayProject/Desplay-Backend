package com.deterior.global.config

import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration
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
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate: RedisTemplate<String, String> = RedisTemplate<String, String>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.connectionFactory = redisConnectionFactory()
        return redisTemplate
    }

    @Bean
    fun cacheManager(factory: RedisConnectionFactory): CacheManager {
        val cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))
            .entryTtl(Duration.ofHours(1))
        return RedisCacheManager
            .RedisCacheManagerBuilder
            .fromConnectionFactory(factory)
            .cacheDefaults(cacheConfig)
            .build()
    }
}