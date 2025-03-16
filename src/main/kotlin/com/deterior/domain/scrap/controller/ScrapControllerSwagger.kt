package com.deterior.domain.scrap.controller

import com.deterior.domain.member.dto.ScrapCheckResponse
import com.deterior.domain.scrap.dto.ScrapRequest
import com.deterior.domain.scrap.dto.ScrapResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "스크랩 API")
interface ScrapControllerSwagger {

    @Operation(summary = "스크랩 하기 \uD83D\uDD12", description = "이전에 스크랩하지 않았다면 스크랩하고, 했다면 취소")
    fun pushScrap(
        @RequestBody scrapRequest: ScrapRequest
    ): ResponseEntity<ScrapResponse>

    @Operation(summary = "스크랩 여부 확인 \uD83D\uDD12",
        parameters = [
            Parameter(name = "boardId", description = "게시글 아이디", required = true),
            Parameter(name = "username", description = "사용자 아이디", required = true),
        ]
    )
    fun checkScrap(
        @RequestParam boardId: Long,
        @RequestParam username: String
    ): ResponseEntity<ScrapCheckResponse>
}