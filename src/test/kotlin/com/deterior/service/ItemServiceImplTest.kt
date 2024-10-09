package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.Board
import com.deterior.domain.board.BoardDto
import com.deterior.domain.board.MoodType
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.image.repository.ImageRepository
import com.deterior.domain.image.service.DBFileUploadService
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.repository.ItemRepository
import com.deterior.domain.item.service.ItemService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemServiceImplTest @Autowired constructor(
    private val databaseCleanup: DatabaseCleanup,
    private val boardRepository: BoardRepository,
    private val itemRepository: ItemRepository,
    private val itemService: ItemService
) : BehaviorSpec({



    afterSpec {
        databaseCleanup.execute()
    }

    Given("Item을 저장하는 서비스가 있다") {
        val board = Board(
            title = "title",
            content = "content",
            mutableListOf(MoodType.OFFICE, MoodType.CALM)
        )
        val result = boardRepository.save(board)
        val boardDto = BoardDto(
            boardId = result.id!!,
            title = result.title,
            content = result.content,
            moodTypes = result.moodTypes,
        )
        val itemSaveDto = ItemSaveDto(
            items = listOf(
                Pair("item1", "https://item1"),
                Pair("item2", "https://item2")
            ),
            boardDto = boardDto
        )
        When("Item을 저장한다") {
            val results = itemService.saveItem(itemSaveDto)
            Then("저장이 성공한다") {
                var cnt = 1
                for (item in results) {
                    item.id shouldBe cnt
                    item.title shouldBe "item${cnt}"
                    item.link shouldBe "https://item${cnt}"
                    item.boardDto.boardId shouldBe result.id
                    item.boardDto.title shouldBe result.title
                    item.boardDto.content shouldBe result.content
                    cnt++
                }
            }
        }
    }

    fun saveBoard(): BoardDto {
        val board = Board(
            title = "title",
            content = "content",
            mutableListOf(MoodType.OFFICE, MoodType.CALM)
        )
        val result = boardRepository.save(board)
        return BoardDto(
            boardId = result.id!!,
            title = result.title,
            content = result.content,
            moodTypes = result.moodTypes,
        )
    }
})