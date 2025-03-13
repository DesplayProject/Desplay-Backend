package com.deterior.global.controller

import com.deterior.global.dto.AutoCompleteGetResponse
import com.deterior.global.dto.AutoCompleteGetRequest
import com.deterior.global.dto.AutoCompleteUpdateRequest
import com.deterior.global.dto.AutoCompleteUpdateResponse
import com.deterior.global.service.AutoCompleteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/auto-complete")
class AutoCompleteController @Autowired constructor(
    private val autoCompleteService: AutoCompleteService
) : AutoCompleteSwagger {
    @GetMapping("/get-string/{input}")
    override fun getAutoCompleteString(
        @PathVariable input: String,
    ): ResponseEntity<AutoCompleteGetResponse> {
        val result = autoCompleteService.getAutoComplete(input)
        return ResponseEntity.ok(result.toResponse())
    }

    @PostMapping("/update-string")
    override fun updateAutoCompleteString(
        @RequestBody autoCompleteUpdateRequest: AutoCompleteUpdateRequest
    ): ResponseEntity<AutoCompleteUpdateResponse> {
        autoCompleteService.updateAutoComplete(autoCompleteUpdateRequest.input)
        return ResponseEntity.ok(AutoCompleteUpdateResponse(autoCompleteUpdateRequest.input))
    }
}