package com.deterior.domain.member.service

import com.deterior.domain.member.repository.MemberRepository
import com.deterior.sercurity.dto.JwtToken
import com.deterior.sercurity.provider.JwtTokenProvider
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberServiceImpl @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
) : MemberService {
    override fun signIn(username: String, password: String): JwtToken {
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        return jwtTokenProvider.generateToken(authentication)
    }
}