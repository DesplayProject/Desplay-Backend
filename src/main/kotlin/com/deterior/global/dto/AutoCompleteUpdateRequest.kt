package com.deterior.global.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "자동완성 문자열 추가 DTO")
data class AutoCompleteUpdateRequest(
    val input: String,
) {
    fun toResponse() = AutoCompleteUpdateResponse(
        input = input,
    )
}