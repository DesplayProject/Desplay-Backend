package com.deterior.global

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refreshToken", timeToLive = 604800000)
class RefreshToken(
    @Id
    val key: String,

    val value: String
)