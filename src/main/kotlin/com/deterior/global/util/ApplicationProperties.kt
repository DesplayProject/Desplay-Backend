package com.deterior.global.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring")
class ApplicationProperties (
    val jwt: Jwt,
    val mail: Mail,
){
    data class Jwt(
        val secret: String
    )
    data class Mail(
        val username: String
    )
}