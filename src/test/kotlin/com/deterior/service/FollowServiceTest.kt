package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.follow.Follow
import com.deterior.domain.follow.dto.FollowingDto
import com.deterior.domain.follow.repository.FollowRepository
import com.deterior.domain.follow.service.FollowService
import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.global.util.InitDBService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FollowServiceTest @Autowired constructor(
    private val followService: FollowService,
    private val followRepository: FollowRepository,
    private val memberRepository: MemberRepository,
    private val databaseCleanup: DatabaseCleanup,
    private val initDBService: InitDBService
) : FunSpec({

    afterEach {
        databaseCleanup.execute()
    }

    test("follow 하고 unfollow") {
        val members = initDBService.fillMembers()
        val follows = initDBService.fillFollows(members)
        for (i in 0..8) {
            follows[i].from.id!! + 1 shouldBe follows[i].to.id
        }
        followRepository.findAll().size shouldBe 10
        followService.unfollowing(FollowingDto(1, 2))
        followRepository.findAll().size shouldBe 9
        followService.unfollowing(FollowingDto(2, 3))
        followRepository.findAll().size shouldBe 8
        followService.unfollowing(FollowingDto(3, 4))
        followRepository.findAll().size shouldBe 7
    }

    test("follower, following 찾기") {
        val members = initDBService.fillMembers()
        for (i in 1..9) {
            val follower = Follow(
                from = members[i],
                to = members[0],
            )
            val following = Follow(
                from = members[0],
                to = members[i],
            )
            followRepository.save(follower)
            followRepository.save(following)
        }
        val followers = followService.findFollowers(members[0].toDto())
        followers.size shouldBe 9
        var count = 2
        for (follow in followers) {
            follow.memberId shouldBe count++
        }
        val followings = followService.findFollowings(members[0].toDto())
        followings.size shouldBe 9
        count = 2
        for (follow in followings) {
            follow.memberId shouldBe count++
        }
    }
})