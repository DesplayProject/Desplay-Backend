package com.deterior.domain.board.controller

import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.dto.BoardWriteResponse
import com.deterior.domain.member.dto.MemberContext
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Tag(name = "게시글 API")
interface BoardControllerSwagger {

    @Operation(summary = "게시글 작성 \uD83D\uDD12", description = "form-data로 전송, files는 DTO와 별개")
    fun writeBoard(
        memberContext: MemberContext,
        @Schema(description = "이미지 파일") files: List<MultipartFile>,
        boardWriteRequest: BoardWriteRequest
    ): ResponseEntity<BoardWriteResponse>

    @Operation(summary = "메인 화면 게시글 검색 \uD83D\uDD12", description = "쿼리 파라미터로 전송",
            parameters = [
                Parameter(name = "tags", description = "태그들", required = false),
                Parameter(name = "keyword", description = "검색 키워드", required = true),
                Parameter(name = "username", description = "글쓴이", required = false),
                Parameter(name = "sortTypes", description = "정렬 타입 [DATE_ASC: 최신순, DATE_DESC: 오래된 순, LIKE_ASC: 스크랩 적은 순, LIKE_DESC: 스크랩 많은 순]", required = true),
                Parameter(name = "searchTypes", description = "검색 타입 [MAIN: 메인 화면 검색, MY_LIKE: 내가 스크랩 한 게시물 검색, MY_WRITE: 내가 작성한 게시물 검색]", required = true),
            ]
        )
    fun boardSearch(
        condition: BoardSearchCondition,
        @Parameter(description = "페이지 설정 \uD83D\uDD12", required = true) pageable: Pageable
    ): ResponseEntity<Page<BoardFindDto>>
}