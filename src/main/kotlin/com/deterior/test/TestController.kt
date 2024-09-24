package com.deterior.test

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {
    @PostMapping("/member/user")
    fun memberUser() = "user"

    @GetMapping("/index")
    fun testIndex() = "test_index"
}