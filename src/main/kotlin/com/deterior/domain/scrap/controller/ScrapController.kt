package com.deterior.domain.scrap.controller

import com.deterior.domain.member.dto.ScrapCheckResponse
import com.deterior.domain.scrap.dto.ScrapHandleDto
import com.deterior.domain.scrap.dto.ScrapRequest
import com.deterior.domain.scrap.dto.ScrapResponse
import com.deterior.domain.scrap.service.ScrapService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api")
@Controller
class ScrapController @Autowired constructor(
    private val scrapService: ScrapService
) : ScrapControllerSwagger {

    @PostMapping("/scrap/push")
    override fun pushScrap(@RequestBody scrapRequest: ScrapRequest): ResponseEntity<ScrapResponse> {
        val dto = scrapService.doLike(scrapRequest.toHandleDto())
        return ResponseEntity.ok(dto.toResponse())
    }

    @GetMapping("/scrap/check")
    override fun checkScrap(
        @RequestParam boardId: Long,
        @RequestParam username: String
    ): ResponseEntity<ScrapCheckResponse> {
        val result = scrapService.isLike(ScrapHandleDto(boardId, username))
        return ResponseEntity.ok(ScrapCheckResponse(result, username))
    }
}