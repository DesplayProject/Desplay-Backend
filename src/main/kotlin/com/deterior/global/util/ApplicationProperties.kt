package com.deterior.global.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring")
class ApplicationProperties (
    val jwt: Jwt,
    val mail: Mail,
    val upload: Upload,
){
    data class Jwt(
        val secret: String
    )
    data class Mail(
        val username: String
    )
    data class Upload(
        val path: String
    )
}