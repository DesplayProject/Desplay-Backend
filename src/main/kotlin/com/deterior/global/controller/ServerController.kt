package com.deterior.global.controller

import com.deterior.global.util.ServerProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ServerController @Autowired constructor(
    val serverProperties: ServerProperties,
){
    @GetMapping("/env")
    fun getEnv(): ResponseEntity<String>{
        return ResponseEntity.ok(serverProperties.env)
    }
}