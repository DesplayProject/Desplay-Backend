package com.deterior.domain.member.dto.response

import com.deterior.domain.member.Member

data class SignUpResponse(
    val username: String,
    val email: String,
) {
    companion object {
        fun toResponse(member: Member): SignUpResponse =
            SignUpResponse(
                username = member.username,
                email = member.email
            )
    }
}