package com.deterior.domain.follow.service

import com.deterior.domain.follow.Follow
import com.deterior.domain.follow.dto.FollowDto
import com.deterior.domain.follow.dto.FollowingDto
import com.deterior.domain.follow.repository.FollowRepository
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class FollowServiceImpl @Autowired constructor(
    private val followRepository: FollowRepository,
    private val memberRepository: MemberRepository
) : FollowService {
    override fun following(followingDto: FollowingDto): FollowDto {
        val fromMember = memberRepository.findById(followingDto.fromId).get()
        val toMember = memberRepository.findById(followingDto.toId).get()
        val follow = followRepository.save(Follow(
            from = fromMember,
            to = toMember
        ))
        return follow.toDto()
    }

    override fun unfollowing(followingDto: FollowingDto): FollowDto {
        val follow = followRepository.findByFromIdAndToId(followingDto.fromId, followingDto.toId)
        followRepository.delete(follow!!)
        return follow.toDto()
    }

    override fun findFollowers(memberDto: MemberDto): List<MemberDto> {
        val followers = followRepository.findByToId(memberDto.memberId)
        return followerToMemberDto(followers)
    }

    override fun findFollowings(memberDto: MemberDto): List<MemberDto> {
        val followers = followRepository.findByFromId(memberDto.memberId)
        return followingToMemberDto(followers)
    }

    private fun followerToMemberDto(followers: List<Follow>): List<MemberDto> = followers.map { follower ->
            val member = memberRepository.findById(follower.from.id!!).get()
            MemberDto(
                memberId = member.id!!,
                username = member.username,
                password = member.password,
                email = member.email,
                roles = member.roles
            )
        }

    private fun followingToMemberDto(followings: List<Follow>): List<MemberDto> = followings.map { following ->
        val member = memberRepository.findById(following.to.id!!).get()
        MemberDto(
            memberId = member.id!!,
            username = member.username,
            password = member.password,
            email = member.email,
            roles = member.roles
        )
    }
}