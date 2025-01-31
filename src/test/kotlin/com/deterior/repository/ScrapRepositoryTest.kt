package com.deterior.repository

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.Board
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.scrap.Scrap
import com.deterior.domain.scrap.dto.ScrapHandleDto
import com.deterior.domain.scrap.repository.ScrapRepository
import com.deterior.global.util.InitDBService
import io.kotest.core.spec.style.AnnotationSpec.AfterEach
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ScrapRepositoryTest @Autowired constructor(
    val scrapRepository: ScrapRepository,
    val databaseCleanup: DatabaseCleanup,
    val initDBService: InitDBService,
    val memberRepository: MemberRepository,
    val boardRepository: BoardRepository,
) : FunSpec({

    afterTest {
        databaseCleanup.execute()
    }

    test("여러가지 scrap 조회") {
        val members = initDBService.fillMembers()
        val boards = initDBService.fillBoards(members)
        initDBService.fillScraps(members, boards)
        for (i in 0..9) {
            val scrapUndoDto = ScrapHandleDto((i + 1).toLong(), "username$i")
            val result = scrapRepository.findScrapByUsernameAndBoardId(scrapUndoDto)
            result.id shouldBe result.board.id
            result.member.username shouldBe "username${i}"
        }
    }

    test("단일 scrap 조회") {
        val member = Member("kanye", "password", "email@mail.com", mutableListOf())
        memberRepository.save(member)
        val board = Board("i hope this test will be successful", "content", member)
        boardRepository.save(board)
        scrapRepository.save(Scrap(member,board))


        val scrapUndoDto = ScrapHandleDto(1, "kanye")
        val result = scrapRepository.findScrapByUsernameAndBoardId(scrapUndoDto)
        result.member.username shouldBe "kanye"
        result.board.title shouldBe "i hope this test will be successful"
    }

    //0,1: 0
    //2,3: 1
    //4,5: 2
})