package com.deterior.domain.follow.dto

import com.deterior.domain.member.dto.MemberDto

data class FollowDto(
    val followId: Long,
    val from: MemberDto,
    val to: MemberDto
)