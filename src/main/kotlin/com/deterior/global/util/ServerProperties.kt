package com.deterior.global.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "server")
class ServerProperties(
    val env: String,
    val port: String
) {

}