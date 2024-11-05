package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.image.dto.FileSaveDto
import com.deterior.domain.image.service.DBFileUploadService
import com.deterior.domain.member.Member
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.global.exception.ImageNotSupportedTypeException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile

@SpringBootTest
class DBFileUploadServiceTest @Autowired constructor(
    private val databaseCleanup: DatabaseCleanup,
    private val dbFileUploadService: DBFileUploadService,
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository,
) : BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

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
    val fileSaveDto = FileSaveDto(
        files = mutableListOf(
            MockMultipartFile("file1", "file1.png", MediaType.IMAGE_PNG_VALUE, "file1".toByteArray()),
            MockMultipartFile("file2", "file2.png", MediaType.IMAGE_PNG_VALUE, "file2".toByteArray()),
        ),
        boardDto = boardDto,
    )

    Given("DB에 파일을 업로드하는 서비스가 있다") {
        When("파일을 저장한다") {
            val results = dbFileUploadService.saveFile(fileSaveDto)
            Then("저장이 성공한다") {
                var cnt = 1
                for (file in results) {
                    file.imageId shouldBe cnt
                    file.originFileName shouldBe "file${cnt}.png"
                    file.boardDto.boardId shouldBe result.id
                    file.boardDto.title shouldBe result.title
                    file.boardDto.content shouldBe result.content
                    cnt++
                }
            }
        }
        When("지원하지 않는 타입으로 저장한다") {
            val save = FileSaveDto(
                files = mutableListOf(
                    MockMultipartFile("file1", "file1.txt", MediaType.IMAGE_PNG_VALUE, "file1".toByteArray())
                ),
                boardDto = boardDto,
            )
            Then("저장이 실패한다") {
                shouldThrow<ImageNotSupportedTypeException> { dbFileUploadService.saveFile(save) }
            }
        }
    }


})