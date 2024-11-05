package com.deterior.global.dto

data class AutoCompleteUpdateRequest(
    val input: String,
) {
    fun toResponse() = AutoCompleteUpdateResponse(
        input = input,
    )
}