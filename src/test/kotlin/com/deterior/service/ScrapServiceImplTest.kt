package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.scrap.dto.ScrapDto
import com.deterior.domain.scrap.dto.ScrapSaveDto
import com.deterior.domain.scrap.service.ScrapService
import com.deterior.domain.board.Board
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.scrap.dto.ScrapUndoDto
import com.deterior.domain.scrap.repository.ScrapRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ScrapServiceImplTest @Autowired constructor(
    val scrapService: ScrapService,
    val scrapRepository: ScrapRepository,
    val databaseCleanup: DatabaseCleanup,
    val boardRepository: BoardRepository,
    val memberRepository: MemberRepository
) : BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

    val members = mutableListOf<Member>()
    val boards = mutableListOf<Board>()
    val savedLikes = mutableListOf<ScrapDto>()

    Given("Scrap를 저장하는 서비스가 있다") {
        for (i in 0..3) {
            val member = memberRepository.save(Member(
                username = "username$i",
                password = "password$i",
                email = "email$i",
                roles = mutableListOf()
            ))
            members.add(member)
        }
        for (i in 0..1) {
            val board = boardRepository.save(Board(
                title = "title$i",
                content = "content$i",
                member = members[0]
            ))
            boards.add(board)
        }
        When("Scrap를 저장한다") {
            //board[0]: member[0], member[1]
            //board[2]: member[2], member[3]
            for (i in 0..3) {
                val likeDto = scrapService.pushLike(
                    ScrapSaveDto(
                        memberDto = members[i].toDto(),
                        boardDto = boards[i / 2].toDto()
                    )
                )
                savedLikes.add(likeDto)
            }
            Then("저장이 성공한다") {
                for (i in 0..3) {
                    val dto = savedLikes[i]
                    dto.memberDto.memberId shouldBe members[i].id
                    dto.boardDto.boardId shouldBe boards[i/2].id
                }
                for (like in scrapRepository.findAll()) {
                    like.board.scrapCount shouldBe 2
                }
            }
        }
        When("Scrap를 취소한다") {
            for (i in 0..3) {
                scrapService.undoLike(ScrapUndoDto(
                    scrapId = i + 1L
                ))
                Then("취소가 성공한다") {
                    scrapRepository.findAll().size shouldBe (3 - i)
                }
            }
            Then("모든 좋아요의 개수가 0이된다") {
                for (board in boardRepository.findAll()) {
                    board.scrapCount shouldBe 0
                }
            }
        }
    }
})