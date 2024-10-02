package com.deterior.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @PostMapping("/test/member/user")
    fun memberUser() = "user"

    @GetMapping("/test/index")
    fun testIndex() = "test_index"
}