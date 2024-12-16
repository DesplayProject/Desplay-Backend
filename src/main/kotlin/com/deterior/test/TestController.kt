package com.deterior.test

import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.member.dto.MemberContext
import com.deterior.global.dto.AutoCompleteGetDto
import com.deterior.global.dto.AutoCompleteGetResponse
import com.deterior.global.service.AutoCompleteService
import com.deterior.global.util.ApplicationProperties
import com.deterior.global.util.InitDBService
import com.deterior.global.util.InitRedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.nio.file.Files

@RestController
@RequestMapping("/test")
class TestController @Autowired constructor(
    val applicationProperties: ApplicationProperties,
    val initDBService: InitDBService,
    val boardRepository: BoardRepository,
    val autoCompleteService: AutoCompleteService,
    val initRedisService: InitRedisService
) {
    init {
        //initDBService.init()
        //initRedisService.init()
    }
    @PostMapping("/member/user")
    fun memberUser(@AuthenticationPrincipal memberContext: MemberContext): String {
        return "${memberContext.username}, ${memberContext.password}, ${memberContext.memberDto}"
    }

    @GetMapping("/index")
    fun testIndex() = "test_index"

    @GetMapping("/image")
    fun testImage(filename: String): ResponseEntity<ByteArray> {
        val path = "${applicationProperties.upload.path}${filename}"
        val image = File(path)
        val header = HttpHeaders()
        header.add("Content-Type", Files.probeContentType(image.toPath()))
        return ResponseEntity(FileCopyUtils.copyToByteArray(image), header, HttpStatus.OK)
    }

    @GetMapping("/search")
    fun testSearch(condition: BoardSearchCondition, pageable: Pageable): Page<BoardFindDto> {
        return boardRepository.selectSearch(condition, pageable)
    }

    @GetMapping("/auto-complete")
    fun testAutoComplete(keyword: String): ResponseEntity<AutoCompleteGetDto> {
        return ResponseEntity.ok(autoCompleteService.getAutoComplete(keyword))
    }
}
