package com.deterior.domain.member.service

import com.deterior.sercurity.dto.JwtToken

interface MemberService {
    fun signIn(username: String, password: String): JwtToken
}