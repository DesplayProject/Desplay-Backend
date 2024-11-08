package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.item.service.ItemService
import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.tag.dto.BoardTagSaveDto
import com.deterior.domain.tag.repository.BoardTagRepository
import com.deterior.domain.tag.repository.TagRepository
import com.deterior.domain.tag.service.BoardTagService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BoardTagServiceImplTest @Autowired constructor(
    private val databaseCleanup: DatabaseCleanup,
    private val boardRepository: BoardRepository,
    private val tagRepository: TagRepository,
    private val boardTagService: BoardTagService,
    private val memberRepository: MemberRepository,
    private val boardTagRepository: BoardTagRepository
): BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

    Given("Tag를 저장하는 서비스가 있다") {
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
            member = saveMember
        )
        val result = boardRepository.save(board)
        val boardDto = BoardDto(
            boardId = result.id!!,
            title = result.title,
            content = result.content,
            scrapCount = result.scrapCount,
        )
        val saveBoardTagDtos = listOf(
            BoardTagSaveDto("1", boardDto),
            BoardTagSaveDto("2", boardDto)
        )
        When("Tag를 저장한다") {
            val results = boardTagService.saveTag(saveBoardTagDtos)
            val savedBoard = boardRepository.findById(board.id!!).get()
            Then("저장이 성공한다") {
                var cnt = 1
                for (tag in results) {
                    tag.boardTagId shouldBe cnt
                    tag.tagDto.title shouldBe cnt.toString()
                    cnt++
                }
                savedBoard.tags.size shouldBe 2
                boardTagRepository.findAll().size shouldBe 2
                boardTagService.saveTag(listOf(
                    BoardTagSaveDto("1", boardDto),
                    BoardTagSaveDto("2", boardDto)
                ))
                tagRepository.findAll().size shouldBe 2
                savedBoard.tags.size shouldBe 2
                boardTagRepository.findAll().size shouldBe 4
            }
        }
    }
})