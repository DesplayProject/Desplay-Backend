package com.deterior.facade

import com.deterior.domain.board.dto.BoardWriteDto
import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.service.BoardService
import com.deterior.domain.image.service.FileUploadService
import com.deterior.domain.item.service.ItemService
import com.deterior.domain.board.facade.BoardFacade
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BoardFacadeTest : BehaviorSpec({

    val boardService = mockk<BoardService>()
    val fileUploadService = mockk<FileUploadService>()
    val itemService = mockk<ItemService>()
    val boardFacade = BoardFacade(
        boardService = boardService,
        fileUploadService = fileUploadService,
        itemService = itemService
    )

    Given("Board와 관련된 비즈니스 로직을 수행하는 facade가 있디") {
        every { boardService.saveBoard(any()) } returns mockk()
        every { fileUploadService.saveFile(any()) } returns listOf()
        every { itemService.saveItem(any()) } returns listOf()
        When("facade를 호출한다") {
            val request = BoardWriteDto(
                title = "title",
                content = "content",
                moodTypes = listOf(),
                files = listOf(),
                items = listOf(),
                memberDto = mockk()
            )
            boardFacade.writeBoard(request)
            Then("service들이 호출된다") {
                verify(exactly = 1) { boardService.saveBoard(any()) }
                verify(exactly = 1) { fileUploadService.saveFile(any()) }
                verify(exactly = 1) { itemService.saveItem(any()) }
            }
        }
    }

})