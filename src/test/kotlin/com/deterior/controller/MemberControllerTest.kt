package com.deterior.controller

import com.deterior.domain.member.dto.SignInRequest
import com.deterior.domain.member.dto.SignUpRequest
import com.deterior.domain.member.dto.SignUpResponse
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.member.service.MemberService
import com.deterior.DatabaseCleanup
import com.deterior.global.util.ApplicationProperties
import com.deterior.sercurity.dto.JwtToken
import com.deterior.sercurity.dto.ReissueTokenRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest (
    @Autowired val memberService: MemberService,
    @Autowired val testRestTemplate: TestRestTemplate,
    @Autowired val databaseCleanup: DatabaseCleanup,
    @Autowired val memberRepository: MemberRepository,
    @LocalServerPort val port: Int
) : BehaviorSpec({
    extensions(SpringExtension)

    val domain: String = "http://localhost"
    val signUpRequest = SignUpRequest(
        username = "username",
        password = "password",
        email = "test@test.com",
    )

    afterSpec {
        databaseCleanup.execute()
    }

    Given("회원가입 컨트롤러가 주어진다") {
        val url = "${domain}:${port}/api/member/sign-up"
        When("회원가입을 한다") {
            val response = testRestTemplate.postForEntity(url, signUpRequest, SignUpResponse::class.java)
            Then("회원가입이 성공한다") {
                val signUpResponse = response.body
                signUpResponse!!.username shouldBe signUpRequest.username
                signUpResponse.email shouldBe signUpRequest.email
            }
        }
    }

    Given("로그인 컨트롤러가 주어진다") {
        var jwtToken: JwtToken? = null
        //memberService.signUp(signUpRequest)
        When("로그인을 한다") {
            val signInRequest = SignInRequest(
                username = "username",
                password = "password",
            )
            jwtToken = memberService.signIn(signInRequest)
            val httpHeader = HttpHeaders()
            httpHeader.setBearerAuth(jwtToken!!.accessToken)
            Then("토튼이 반환되어 다른 url에 접근이 가능하다") {
                val url: String = "${domain}:${port}/test/member/user"
                val response = testRestTemplate.postForEntity(url, HttpEntity<HttpHeaders>(httpHeader), String::class.java)
                response.statusCode shouldBe HttpStatus.OK
            }
        }
        When("토큰 재발급을 한다") {
            val httpHeader = HttpHeaders()
            httpHeader.setBearerAuth(jwtToken!!.accessToken)
            val reissueTokenRequest = ReissueTokenRequest(
                accessToken = jwtToken!!.accessToken,
                refreshToken = jwtToken!!.refreshToken
            )
            val request = HttpEntity(reissueTokenRequest, httpHeader)
            Then("재발급이 성공한다") {
                delay(1000)
                val url: String = "${domain}:${port}/api/member/reissue"
                val response = testRestTemplate.postForEntity(url, request, JwtToken::class.java)
                response.statusCode shouldBe HttpStatus.OK
                response.body!!.accessToken shouldNotBe jwtToken!!.accessToken
                response.body!!.refreshToken shouldNotBe jwtToken!!.refreshToken
            }
        }
    }

})
