package com.deterior.domain.member

import com.deterior.domain.entitiy.BaseEntity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class Member (
    private var username: String,
    private var password: String,
    private var email: String,
) : BaseEntity(), UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableList<String> = mutableListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()
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