package com.deterior.domain.follow.service

import com.deterior.domain.follow.dto.FollowDto
import com.deterior.domain.follow.dto.FollowingDto
import com.deterior.domain.member.dto.MemberDto

interface FollowService {
    fun following(followingDto: FollowingDto): FollowDto
    fun unfollowing(followingDto: FollowingDto): FollowDto
    fun findFollowers(memberDto: MemberDto): List<MemberDto>
    fun findFollowings(memberDto: MemberDto): List<MemberDto>
}