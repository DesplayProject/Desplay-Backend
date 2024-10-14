package com.deterior.global.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring")
class ApplicationProperties (
    val jwt: Jwt,
    val mail: Mail,
    val upload: Upload,
    val data: Data
){
    data class Jwt(
        val secret: String,
        val token: Token
    ) {
        data class Token(
            val accessExpirationTime: Long,
            val refreshExpirationTime: Long,
            val reissueTime: Long,
        )
    }

    data class Mail(
        val username: String
    )

    data class Upload(
        val path: String
    )

    data class Data(
        val redis: Redis,
    ) {
        data class Redis(
            val host: String,
            val port: Int
        )
    }
}