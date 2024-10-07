package com.deterior.service

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.Board
import com.deterior.domain.board.MoodType
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.image.Image
import com.deterior.domain.image.dto.FileUploadDto
import com.deterior.domain.image.repository.ImageRepository
import com.deterior.domain.image.service.DBFileUploadService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

@SpringBootTest
class DBFileUploadServiceTest @Autowired constructor(
    private val databaseCleanup: DatabaseCleanup,
    private val dbFileUploadService: DBFileUploadService,
    private val imageRepository: ImageRepository,
    private val boardRepository: BoardRepository,
) : BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

    Given("DB에 파일을 업로드하는 서비스가 있다") {
        val board = Board(
            title = "title",
            content = "content",
            mutableListOf(MoodType.OFFICE, MoodType.CALM)
        )
        boardRepository.save(board)
        val fileUploadDto = FileUploadDto(
            board = board,
            files = mutableListOf(
                MockMultipartFile("file1", "file1.png", MediaType.IMAGE_PNG_VALUE, "file1".toByteArray()),
                MockMultipartFile("file2", "file2.png", MediaType.IMAGE_PNG_VALUE, "file2".toByteArray()),
            )
        )
        When("파일을 저장한다") {
            dbFileUploadService.saveFile(fileUploadDto)
            Then("저장이 성공한다") {
                val imageResult = imageRepository.findById(1).get()
                val boardResult = imageResult.board
                imageResult.originFileName shouldBe "file1.png"
                boardResult.images shouldContain imageResult
            }
        }
    }
})