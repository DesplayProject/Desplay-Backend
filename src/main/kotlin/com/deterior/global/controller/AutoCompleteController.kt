package com.deterior.global.controller

import com.deterior.global.dto.AutoCompleteDto
import com.deterior.global.dto.AutoCompleteGetRequest
import com.deterior.global.dto.AutoCompleteUpdateRequest
import com.deterior.global.service.AutoCompleteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/auto-complete")
class AutoCompleteController @Autowired constructor(
    private val autoCompleteService: AutoCompleteService
) {
    @GetMapping("/get-string")
    fun getAutoCompleteString(
        @RequestBody autoCompleteGetRequest: AutoCompleteGetRequest
    ): ResponseEntity<AutoCompleteDto> {
        val result = autoCompleteService.getAutoComplete(autoCompleteGetRequest.input)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/update-string")
    fun updateAutoCompleteString(
        @RequestBody autoCompleteUpdateRequest: AutoCompleteUpdateRequest
    ) {
        autoCompleteService.updateAutoComplete(autoCompleteUpdateRequest.input)
    }
}