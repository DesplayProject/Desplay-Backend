package com.deterior.domain.member.service

import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.SignInRequest
import com.deterior.domain.member.dto.SignUpRequest
import com.deterior.domain.member.dto.SignUpResponse
import com.deterior.global.exception.DuplicateEmailException
import com.deterior.global.exception.DuplicateUsernameException
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.global.exception.ErrorCode
import com.deterior.sercurity.dto.JwtToken
import com.deterior.sercurity.provider.JwtTokenProvider
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberServiceImpl @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) : MemberService {
    override fun signIn(signInRequest: SignInRequest): JwtToken {
        val authenticationToken = UsernamePasswordAuthenticationToken(signInRequest.username, signInRequest.password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        return jwtTokenProvider.generateToken(authentication)
    }

    override fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        val username = signUpRequest.username
        val email = signUpRequest.email
        if(memberRepository.existsByUsername(username)) {
            throw DuplicateUsernameException("중복된 username입니다.", username, ErrorCode.DUPLICATE_USERNAME)
        }
        if(memberRepository.existsByEmail(email)) {
            throw DuplicateEmailException("중복된 email입니다.", email, ErrorCode.DUPLICATE_EMAIL)
        }
        signUpRequest.password = passwordEncoder.encode(signUpRequest.password)
        val roles = mutableListOf("USER")
        val member = Member.toEntity(
            request = signUpRequest,
            roles = roles,
        )
        memberRepository.save(member)
        return SignUpResponse.toResponse(member)
    }
}