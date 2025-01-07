package com.deterior.global.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServerController {
    @Value("\${spring.profiles.active}")
    private lateinit var env: String

    @GetMapping("/env")
    fun env(): String {
        return if (env.endsWith("-server")) env.removeSuffix("-server") else env
    }
}
