package com.deterior.controller

import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.facade.BoardFacade
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import java.nio.charset.StandardCharsets

@WebMvcTest
@MockBean(JpaMetamodelMappingContext::class)
class BoardControllerTest(
    @Autowired val mockMvc: MockMvc,
    @MockBean val boardFacade: BoardFacade,
    @Autowired val objectMapper: ObjectMapper
) : BehaviorSpec({
    Given("Board controller가 존재한다") {
        val boardWriteRequest: BoardWriteRequest = BoardWriteRequest(
            title = "title",
            content = "content",
            moodTypes = listOf(),
            items = listOf(
                Pair("desk","http://coupang/desk"),
                Pair("computer","http://coupang/computer"),
            ),
        )
        val content = objectMapper.writeValueAsString(boardWriteRequest)
        val request = MockMultipartFile("boardWriteRequest", "boardWriteRequest.json", "application/json", content.toByteArray(StandardCharsets.UTF_8))
        val images = listOf(
            MockMultipartFile("files", "test1.txt", "text/plain", "test1".toByteArray()),
            MockMultipartFile("files", "test2.txt", "text/plain", "test2".toByteArray()),
        )
        When("controller로 Board를 write하는 요청을 보낸다") {

        }
    }
})