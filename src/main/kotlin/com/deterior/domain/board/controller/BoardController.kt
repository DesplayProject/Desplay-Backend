package com.deterior.domain.board.controller

import com.deterior.domain.board.dto.BoardWriteDto
import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.dto.BoardWriteResponse
import com.deterior.domain.board.facade.BoardFacade
import com.deterior.domain.member.dto.MemberContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/api")
class BoardController @Autowired constructor(
    val boardFacade: BoardFacade
) {
    @PostMapping("/board/write")
    fun writeBoard(
        @AuthenticationPrincipal memberContext: MemberContext,
        @RequestPart files: List<MultipartFile>,
        @RequestPart boardWriteRequest: BoardWriteRequest
    ): ResponseEntity<BoardWriteResponse> {
        val response = boardFacade.writeBoard(boardWriteRequest.toWriteDto(files, memberContext))
        return ResponseEntity.ok(response)
    }
}