package com.deterior.domain.controller

import com.deterior.domain.member.dto.SignInDto
import com.deterior.domain.member.service.MemberService
import com.deterior.sercurity.dto.JwtToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class MemberController @Autowired constructor(
    private val memberService: MemberService
) {
    @PostMapping("/api/member/sign-in")
    fun signIn(
        @RequestBody signInDto: SignInDto
    ): ResponseEntity<JwtToken> {
        val username = signInDto.username
        val password = signInDto.password
        val jwtToken = memberService.signIn(username, password)
        return ResponseEntity.ok(jwtToken)
    }
}