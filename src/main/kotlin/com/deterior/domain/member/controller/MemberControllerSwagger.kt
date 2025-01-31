package com.deterior.domain.member.controller

import com.deterior.domain.member.dto.*
import com.deterior.global.dto.JwtToken
import com.deterior.global.dto.ReissueTokenRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "인증 API", description = "인증이 필요한 다른 API에 접근할 때는 이 API를 이용하여 access token을 얻어 Authorization 헤더에 Bearer 토큰으로 추가해야 합니다.")
interface MemberControllerSwagger {

    @Operation(summary = "로그인")
    fun signIn(signInRequest: SignInRequest): ResponseEntity<JwtToken>

    @Operation(summary = "회원가입")
    fun signUp(signUpRequest: SignUpRequest): ResponseEntity<SignUpResponse>

    @Operation(summary = "토큰 재발급", description = "JWT 토큰이 만료되었을 때 요청")
    fun reissueToken(reissueTokenRequest: ReissueTokenRequest): ResponseEntity<JwtToken>

    @Operation(summary = "비밀번호 초기화")
    fun resetPassword(passwordResetRequest: PasswordResetRequest): ResponseEntity<PasswordResetResponse>
}