package com.deterior.sercurity

import com.deterior.domain.BaseEntity
import jakarta.persistence.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refreshToken", timeToLive = 604800000)
class RefreshToken(
    @Id
    val key: String,

    val value: String
)