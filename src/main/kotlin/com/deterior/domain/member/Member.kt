package com.deterior.domain.member

import com.deterior.domain.BaseEntity
import com.deterior.domain.member.dto.request.SignUpRequest
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class Member (
    private var username: String,

    private var password: String,

    var email: String,

    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableList<String> = mutableListOf()
) : BaseEntity(), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()
    }

    companion object {
        fun toEntity(request: SignUpRequest, roles: MutableList<String>): Member =
            Member(
                username = request.username,
                password = request.password,
                email = request.email,
                roles = roles
            )
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}