package com.deterior.domain.member.dto

import com.deterior.domain.member.Member

data class MemberDto(
    val memberId: Long,
    val username: String,
    val password: String,
    val email: String,
    val roles: List<String>
)