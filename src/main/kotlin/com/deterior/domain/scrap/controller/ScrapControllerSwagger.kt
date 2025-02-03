package com.deterior.domain.scrap.controller

import com.deterior.domain.scrap.dto.ScrapRequest
import com.deterior.domain.scrap.dto.ScrapResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "스크랩 API")
interface ScrapControllerSwagger {

    @Operation(summary = "스크랩 하기 \uD83D\uDD12", description = "이전에 스크랩하지 않았다면 스크랩하고, 했다면 취소")
    fun pushScrap(
        @RequestBody scrapRequest: ScrapRequest
    ): ResponseEntity<ScrapResponse>
}