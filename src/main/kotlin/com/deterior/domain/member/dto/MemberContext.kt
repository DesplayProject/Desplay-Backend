package com.deterior.domain.member.dto

import com.deterior.domain.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class MemberContext(
    val memberDto: MemberDto,
    val pwd: String,
    val roles: MutableList<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
    }

    override fun getPassword(): String {
        return pwd
    }

    override fun getUsername(): String {
        return memberDto.username
    }
}