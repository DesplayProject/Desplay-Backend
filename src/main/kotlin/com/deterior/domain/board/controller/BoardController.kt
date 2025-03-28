package com.deterior.domain.board.controller

import com.deterior.domain.board.dto.*
import com.deterior.domain.board.facade.BoardFacade
import com.deterior.domain.board.service.BoardService
import com.deterior.domain.member.dto.MemberContext
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/api")
class BoardController @Autowired constructor(
    private val boardFacade: BoardFacade,
    private val boardService: BoardService
) : BoardControllerSwagger {

    @PostMapping("/board/write")
    override fun writeBoard(
        @AuthenticationPrincipal memberContext: MemberContext,
        @RequestPart files: List<MultipartFile>,
        @RequestPart boardWriteRequest: BoardWriteRequest
    ): ResponseEntity<BoardWriteResponse> {
        val response = boardFacade.writeBoard(boardWriteRequest.toWriteDto(files, memberContext))
        return ResponseEntity.ok(response)
    }

    @GetMapping("/board/search")
    override fun boardSearch(condition: BoardSearchCondition, pageable: Pageable): ResponseEntity<Page<BoardFindDto>> {
        return ResponseEntity.ok(boardService.selectSearch(condition, pageable))
    }

    @GetMapping("/board/{boardId}")
    override fun boardDetail(@PathVariable boardId: Long): ResponseEntity<BoardFindDto> {
        return ResponseEntity.ok(boardService.getBoardDetail(boardId))
    }
}