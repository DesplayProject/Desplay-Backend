package com.deterior.domain.member.service

import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.*
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.global.exception.*
import com.deterior.global.dto.JwtToken
import com.deterior.global.dto.ReissueTokenRequest
import com.deterior.global.service.JwtProvider
import com.deterior.global.util.JwtUtils
import com.deterior.global.repository.RefreshTokenRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberServiceImpl @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtUtils: JwtUtils,
    private val userDetailsService: UserDetailsService
) : MemberService {
    override fun signIn(signInRequest: SignInRequest): JwtToken {
        val authenticationToken = UsernamePasswordAuthenticationToken(signInRequest.username, signInRequest.password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        val jwtToken = jwtUtils.generateToken(authentication)
        refreshTokenRepository.save(jwtToken.toRefreshToken(authentication))
        return jwtToken
    }

    override fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        val username = signUpRequest.username
        val email = signUpRequest.email
        if(memberRepository.existsByUsername(username)) {
            throw DuplicateUsernameException(ErrorCode.DUPLICATE_USERNAME.message, username, ErrorCode.DUPLICATE_USERNAME)
        }
        if(memberRepository.existsByEmail(email)) {
            throw DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL.message, email, ErrorCode.DUPLICATE_EMAIL)
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

    override fun reissue(reissueTokenRequest: ReissueTokenRequest): JwtToken {
        val refreshToken = reissueTokenRequest.refreshToken
        if (!jwtUtils.validateToken(refreshToken) || jwtUtils.isReissueRefreshToken(refreshToken)) {
            throw InvalidJwtTokenException(ErrorCode.INVALID_TOKEN.message, refreshToken, ErrorCode.INVALID_TOKEN)
        }

        val username = jwtUtils.parseClaims(refreshToken).subject
        val principal = userDetailsService.loadUserByUsername(username)
        val accessToken = jwtUtils.createAccessToken(principal.username, principal.authorities)
        return JwtToken("Bearer", accessToken, refreshToken)
    }

    override fun resetPassword(passwordResetRequest: PasswordResetRequest): PasswordResetResponse {
        val member = memberRepository.findByEmail(passwordResetRequest.email)
            .orElseThrow{ AuthenticationFailException(ErrorCode.UNREGISTERD_EMAIL.message, passwordResetRequest.email, ErrorCode.UNREGISTERD_EMAIL) }
        member.password = passwordEncoder.encode(passwordResetRequest.newPassword)
        return PasswordResetResponse(
            username = member.username,
            password = member.password
        )
    }
}