package com.deterior.global

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring")
class ApplicationProperties (
    val jwt: Jwt
){
    data class Jwt(
        val secret: String
    )
}