package com.deterior.domain.member.service

import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@Service
class JwtUserDetailsService @Autowired constructor(
    private val memberRepository: MemberRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return memberRepository.findByUsername(username!!).createUserDetails()
    }

    private fun Member.createUserDetails(): UserDetails {
        return User.builder()
            .username(this.username)
            .password(this.password)
            .roles(*this.roles.toTypedArray())
            .build()
    }
}