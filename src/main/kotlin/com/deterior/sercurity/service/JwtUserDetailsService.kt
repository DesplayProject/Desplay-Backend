package com.deterior.sercurity.service

import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.MemberContext
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService @Autowired constructor(
    private val memberRepository: MemberRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val member = memberRepository.findByUsername(username!!)
        if (member == null) {
            throw UsernameNotFoundException("${username}이라는 아이디를 찾을 수 없습니다")
        }
        val roles: MutableList<GrantedAuthority> = member.roles
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()
        val memberDto = member.toDto()
        return MemberContext(memberDto, roles)
    }
}