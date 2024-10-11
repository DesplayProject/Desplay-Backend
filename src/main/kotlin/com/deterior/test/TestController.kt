package com.deterior.test

import com.deterior.domain.member.dto.MemberContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @PostMapping("/test/member/user")
    fun memberUser(@AuthenticationPrincipal memberContext: MemberContext): String {
        return "${memberContext.username}, ${memberContext.password}, ${memberContext.memberDto}"
    }

    @GetMapping("/test/index")
    fun testIndex() = "test_index"
}