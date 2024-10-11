package com.deterior.service

import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.SignUpRequest
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.member.service.MemberService
import com.deterior.global.exception.DuplicateEmailException
import com.deterior.global.exception.DuplicateUsernameException
import com.deterior.DatabaseCleanup
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceImplTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
    private val databaseCleanup: DatabaseCleanup,
) : BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

    val signUpRequest = SignUpRequest(
        username = "username",
        password = "password",
        email = "test@test.com",
    )

    Given("DB에 member가 저장되어 있다") {
        memberRepository.save(
            Member.toEntity(
                signUpRequest,
                mutableListOf("USER")
            )
        )
        When("중복된 아이디로 회원가입을 한다") {
            val request = SignUpRequest(
                username = "username",
                password = "password1",
                email = "test1@test.com",
            )
            Then("예외가 발생한다") {
                shouldThrow<DuplicateUsernameException> { memberService.signUp(request) }
            }
        }
        When("중복된 이메일로 회원가입을 한다") {
            val request = SignUpRequest(
                username = "username1",
                password = "password1",
                email = "test@test.com",
            )
            Then("예외가 발생한다") {
                shouldThrow<DuplicateEmailException> { memberService.signUp(request) }
            }
        }
    }
})