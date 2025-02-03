package com.deterior.domain.scrap.controller

import com.deterior.domain.scrap.dto.ScrapRequest
import com.deterior.domain.scrap.dto.ScrapResponse
import com.deterior.domain.scrap.service.ScrapService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

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
}