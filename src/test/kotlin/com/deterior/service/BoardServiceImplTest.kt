package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.service.BoardService
import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BoardServiceImplTest @Autowired constructor(
    private val boardService: BoardService,
    private val databaseCleanup: DatabaseCleanup,
    private val memberRepository: MemberRepository
) : BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

    Given("Board를 저장하는 서비스가 있다") {
        val member = Member(
            username = "username",
            password = "password",
            email = "test@test.com",
            roles = mutableListOf()
        )
        val saveMember = memberRepository.save(member)
        val memberDto = saveMember.toDto()
        val boardSaveDto = BoardSaveDto(
            title = "title",
            content = "content",
            moodTypes = listOf(),
            memberDto = memberDto
        )
        When("Board를 저장한다") {
            val savedBoard = boardService.saveBoard(boardSaveDto)
            val boardDto = boardService.findBoardById(savedBoard.boardId)
            Then("저장이 성공한다") {
                boardDto.title shouldBe boardSaveDto.title
                boardDto.content shouldBe boardSaveDto.content
                boardDto.moodTypes shouldBe boardSaveDto.moodTypes
            }
        }
    }
})