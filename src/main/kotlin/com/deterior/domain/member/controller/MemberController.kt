package com.deterior.domain.member.controller

import com.deterior.domain.member.dto.*
import com.deterior.global.dto.ReissueTokenRequest
import com.deterior.domain.member.service.MemberService
import com.deterior.global.dto.JwtToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/member")
class MemberController @Autowired constructor(
    private val memberService: MemberService
) : MemberControllerSwagger {

    @PostMapping("/sign-in")
    override fun signIn(
        @RequestBody signInRequest: SignInRequest
    ): ResponseEntity<JwtToken> {
        val jwtToken = memberService.signIn(signInRequest)
        return ResponseEntity.ok(jwtToken)
    }

    @PostMapping("/sign-up")
    override fun signUp(
        @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<SignUpResponse> {
        val signUpResponse = memberService.signUp(signUpRequest)
        return ResponseEntity.ok(signUpResponse)
    }

    @PostMapping("/reissue")
    override fun reissueToken(
        @RequestBody reissueTokenRequest: ReissueTokenRequest
    ): ResponseEntity<JwtToken> {
        val jwtToken = memberService.reissue(reissueTokenRequest)
        return ResponseEntity.ok(jwtToken)
    }

    @PostMapping("/reset-password")
    override fun resetPassword(
        @RequestBody passwordResetRequest: PasswordResetRequest
    ): ResponseEntity<PasswordResetResponse> {
        val response = memberService.resetPassword(passwordResetRequest)
        return ResponseEntity.ok(response)
    }
}