package com.deterior.domain.member.service

import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.*
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.global.exception.*
import com.deterior.sercurity.dto.JwtToken
import com.deterior.sercurity.dto.ReissueTokenRequest
import com.deterior.sercurity.provider.JwtTokenProvider
import com.deterior.sercurity.repository.RefreshTokenRepository
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
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) : MemberService {
    override fun signIn(signInRequest: SignInRequest): JwtToken {
        val authenticationToken = UsernamePasswordAuthenticationToken(signInRequest.username, signInRequest.password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        val jwtToken = jwtTokenProvider.generateToken(authentication)
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
        if (!jwtTokenProvider.validateToken(reissueTokenRequest.refreshToken)) {
            throw InvalidJwtTokenException(ErrorCode.INVALID_TOKEN.message, reissueTokenRequest.refreshToken, ErrorCode.INVALID_TOKEN)
        }
        val authentication = jwtTokenProvider.authenticate(reissueTokenRequest.accessToken)
        val refreshToken = refreshTokenRepository.findById(authentication.name).orElseThrow { RuntimeException("로그아웃된 사용자") }
        if (refreshToken.value != reissueTokenRequest.refreshToken) {
            throw InConsistentJwtTokenException(ErrorCode.INCONSISTENT_TOKEN.message, reissueTokenRequest.refreshToken, ErrorCode.INCONSISTENT_TOKEN)
        }
        val jwtToken = jwtTokenProvider.generateToken(authentication)
        refreshTokenRepository.save(jwtToken.toRefreshToken(authentication))
        return jwtToken
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