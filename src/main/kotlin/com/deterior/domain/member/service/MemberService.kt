package com.deterior.domain.member.service

import com.deterior.domain.member.dto.request.SignInRequest
import com.deterior.domain.member.dto.request.SignUpRequest
import com.deterior.domain.member.dto.response.SignUpResponse
import com.deterior.sercurity.dto.JwtToken

interface MemberService {
    fun signIn(signInRequest: SignInRequest): JwtToken
    fun signUp(signUpRequest: SignUpRequest): SignUpResponse
}