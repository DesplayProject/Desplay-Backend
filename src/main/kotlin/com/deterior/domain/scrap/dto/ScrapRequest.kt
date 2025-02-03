package com.deterior.domain.scrap.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "스크랩 DTO")
data class ScrapRequest(

    @Schema(description = "게시글 ID", required = true)
    val boardId: Long,

    @Schema(description = "아이디", required = true)
    val username: String,
) {
    fun toHandleDto() = ScrapHandleDto(
        boardId = boardId,
        username = username,
    )
}