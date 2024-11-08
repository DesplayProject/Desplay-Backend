package com.deterior.controller

import com.deterior.domain.board.controller.BoardController
import com.deterior.domain.board.dto.BoardWriteDto
import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.dto.BoardWriteResponse
import com.deterior.domain.board.facade.BoardFacade
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.member.dto.SignInRequest
import com.deterior.domain.member.dto.SignUpRequest
import com.deterior.domain.member.service.MemberService
import com.deterior.domain.member.service.MemberServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
import java.nio.charset.StandardCharsets

@WebMvcTest(controllers = [BoardController::class])
@MockBean(JpaMetamodelMappingContext::class)
class BoardControllerTest(
    @Autowired val mockMvc: MockMvc,
    @MockBean val boardFacade: BoardFacade,
    @Autowired val objectMapper: ObjectMapper,
) : BehaviorSpec({
})