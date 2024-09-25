package com.deterior.domain.controller

import com.deterior.domain.member.dto.request.SignInRequest
import com.deterior.domain.member.dto.request.SignUpRequest
import com.deterior.domain.member.dto.response.SignUpResponse
import com.deterior.domain.member.service.MemberService
import com.deterior.sercurity.dto.JwtToken
import com.deterior.util.DatabaseCleanup
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest (
    @Autowired val memberService: MemberService,
    @Autowired val testRestTemplate: TestRestTemplate,
    @Autowired val databaseCleanup: DatabaseCleanup,
    @LocalServerPort val port: Int
) : AnnotationSpec() {

    val domain: String = "http://localhost"

    @AfterEach
    fun cleanup() {
        databaseCleanup.execute()
    }

    @Test
    fun `회원가입`() {
        val url = "${domain}:${port}/api/member/sign-up"
        val request = SignUpRequest(
            username = "username",
            password = "password",
            email = "test@test.com",
        )
        val response = testRestTemplate.postForEntity(url, request, SignUpResponse::class.java)
        val signUpResponse = response.body
        signUpResponse!!.username shouldBe request.username
        signUpResponse.email shouldBe request.email
    }

    @Test
    fun `로그인`() {
        val signUpRequest = SignUpRequest(
            username = "username",
            password = "password",
            email = "test@test.com",
        )
        val signInRequest = SignInRequest(
            username = "username",
            password = "password",
        )
        memberService.signUp(signUpRequest)
        val jwtToken = memberService.signIn(signInRequest)
        val httpHeader = HttpHeaders()

        httpHeader.setBearerAuth(jwtToken.accessToken)
        //httpHeader.contentType = MediaType.APPLICATION_JSON
        //println(httpHeader)
        val url: String = "${domain}:${port}/test/member/user"

        val response = testRestTemplate.postForEntity(url, HttpEntity<HttpHeaders>(httpHeader), String::class.java)
        response.statusCode shouldBe HttpStatus.OK
        response.body shouldBe "user"
    }
}