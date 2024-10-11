package com.deterior.domain.member.service

import com.deterior.domain.member.dto.SignInRequest
import com.deterior.domain.member.dto.SignUpRequest
import com.deterior.domain.member.dto.SignUpResponse
import com.deterior.sercurity.dto.JwtToken

interface MemberService {
    fun signIn(signInRequest: SignInRequest): JwtToken
    fun signUp(signUpRequest: SignUpRequest): SignUpResponse
}