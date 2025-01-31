package com.deterior.domain.scrap.dto

data class ScrapRequest(
    val boardId: Long,
    val username: String,
) {
    fun toHandleDto() = ScrapHandleDto(
        boardId = boardId,
        username = username,
    )
}