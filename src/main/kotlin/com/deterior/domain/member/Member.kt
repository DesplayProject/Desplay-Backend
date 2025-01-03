package com.deterior.domain.member

import com.deterior.domain.BaseEntity
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.member.dto.SignUpRequest
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class Member (
    val username: String,

    var password: String,

    var email: String,

    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableList<String> = mutableListOf()
) : BaseEntity() {

    fun toDto(): MemberDto = MemberDto(
        memberId = id!!,
        username = username,
        //password = password,
        email = email,
        roles = roles
    )

    companion object {
        fun toEntity(request: SignUpRequest, roles: MutableList<String>): Member =
            Member(
                username = request.username,
                password = request.password,
                email = request.email,
                roles = roles
            )
    }
}