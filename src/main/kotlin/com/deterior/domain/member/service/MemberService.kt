package com.deterior.domain.member.service

import com.deterior.domain.member.dto.SignInRequest
import com.deterior.domain.member.dto.SignUpRequest
import com.deterior.domain.member.dto.SignUpResponse
import com.deterior.sercurity.dto.JwtToken
import com.deterior.sercurity.dto.ReissueTokenRequest

interface MemberService {
    fun signIn(signInRequest: SignInRequest): JwtToken
    fun signUp(signUpRequest: SignUpRequest): SignUpResponse
    fun reissue(reissueTokenRequest: ReissueTokenRequest): JwtToken
}