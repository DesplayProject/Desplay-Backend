package com.deterior.global.controller

import com.deterior.global.dto.AutoCompleteGetResponse
import com.deterior.global.dto.AutoCompleteUpdateRequest
import com.deterior.global.dto.AutoCompleteUpdateResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "자동완성 API")
interface AutoCompleteSwagger {

    @Operation(summary = "자동완성 문자열 가져오기", description = "키워드의 단어로 시작하는 문자열들을 검색량 순으로 가져옵니다.")
    fun getAutoCompleteString(input: String): ResponseEntity<AutoCompleteGetResponse>

    @Operation(summary = "자동완성 문자열 추가", description = "자동완성될 문자열을 추가합니다.")
    fun updateAutoCompleteString(@Parameter(description = "추가할 문자열", required = true) input: String): ResponseEntity<AutoCompleteUpdateResponse>
}