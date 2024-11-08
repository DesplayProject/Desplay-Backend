package com.deterior.global.dto

data class AutoCompleteGetDto(
    val list: List<String>?
) {
    fun toResponse() = AutoCompleteGetResponse(
        list = this.list
    )
}