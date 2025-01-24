package com.deterior.controller

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.controller.BoardController
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardWriteDto
import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.dto.BoardWriteResponse
import com.deterior.domain.board.facade.BoardFacade
import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.member.dto.SignInRequest
import com.deterior.domain.member.dto.SignUpRequest
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.member.service.MemberService
import com.deterior.domain.member.service.MemberServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import java.nio.charset.StandardCharsets

//@WebMvcTest(controllers = [BoardController::class])
//@MockBean(JpaMetamodelMappingContext::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardControllerTest(
    @Autowired val testRestTemplate: TestRestTemplate,
    @Autowired val databaseCleanup: DatabaseCleanup,
    @Autowired val memberRepository: MemberRepository,
    @Autowired val memberService: MemberService,
    @Autowired val passwordEncoder: PasswordEncoder,
    @LocalServerPort val port: Int
) : FunSpec({
    extensions(SpringExtension)

    lateinit var accessToken: String
    val domain = "http://localhost"

    beforeSpec {
        val signUpRequest = SignUpRequest(
            username = "username",
            password = "password",
            email = "test@test.com",
        )
        memberService.signUp(signUpRequest)
        val token = memberService.signIn(SignInRequest(username = "username", password = "password"))
        accessToken = token.accessToken
    }

    afterSpec {
        databaseCleanup.execute()
    }

    test("게시물 검색") {
        val url = "${domain}:${port}/api/board/search"
        val query: MultiValueMap<String, String> = LinkedMultiValueMap()
        query.add("page", "0")
        query.add("size", "100")
        query.add("keyword", "title")
        query.add("sortTypes", "DATE_ASC")
        query.add("searchType", "MAIN")
        val uri = UriComponentsBuilder.fromUriString(url)
            .queryParams(query)
            .build().toUriString()

        val httpHeader = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }
        val entity = HttpEntity<String>(httpHeader)
        val response = testRestTemplate.exchange(
            uri,
            HttpMethod.GET,
            entity,
            String::class.java
        )

        response.statusCode shouldBe HttpStatus.OK
    }
})