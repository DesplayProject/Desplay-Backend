package com.deterior.util

import com.deterior.sercurity.RefreshToken
import com.deterior.sercurity.repository.RefreshTokenRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RedisLearningTest @Autowired constructor(
    val refreshTokenRepository: RefreshTokenRepository
) : BehaviorSpec({
    Given("redis를 사용하는 refreshToken dao가 있다") {
        val refreshToken = RefreshToken(
            key = "user",
            value = "token"
        )
        When("엔티티를 저장한다") {
            refreshTokenRepository.save(refreshToken)
            Then("저장이 성공한다") {
                val result = refreshTokenRepository.findById("user").get()
                result.value shouldBe refreshToken.value
            }
        }
        When("저장된 엔티티를 중복 저장을 이용하여 수정한다") {
            val editToken = RefreshToken(
                key = "user",
                value = "tokenEdit"
            )
            refreshTokenRepository.save(editToken)
            Then("수정이 성공한다") {
                val result = refreshTokenRepository.findById("user").get()
                result.value shouldBe editToken.value
            }
        }
    }
}){
}