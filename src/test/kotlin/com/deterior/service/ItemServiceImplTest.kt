package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.MoodType
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.repository.ItemRepository
import com.deterior.domain.item.service.ItemService
import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class ItemServiceImplTest @Autowired constructor(
    private val databaseCleanup: DatabaseCleanup,
    private val boardRepository: BoardRepository,
    private val itemService: ItemService,
    private val memberRepository: MemberRepository
) : BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

    Given("Item을 저장하는 서비스가 있다") {
        val member = Member(
            username = "username",
            password = "password",
            email = "test@test.com",
            roles = mutableListOf()
        )
        val saveMember = memberRepository.save(member)
        val board = Board(
            title = "title",
            content = "content",
            mutableListOf(MoodType.OFFICE, MoodType.CALM),
            member = saveMember
        )
        val result = boardRepository.save(board)
        val boardDto = BoardDto(
            boardId = result.id!!,
            title = result.title,
            content = result.content,
            moodTypes = result.moodTypes,
        )
        val itemSaveDtos = listOf(
            ItemSaveDto("item1", "https://item1", boardDto),
            ItemSaveDto("item2", "https://item2", boardDto),
        )
        When("Item을 저장한다") {
            val results = itemService.saveItem(itemSaveDtos)
            Then("저장이 성공한다") {
                var cnt = 1
                for (item in results) {
                    item.itemId shouldBe cnt
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
})