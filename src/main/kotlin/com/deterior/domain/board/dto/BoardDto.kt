package com.deterior.domain.board.dto

import com.deterior.domain.board.Board

data class BoardDto(
    val boardId: Long,
    val title: String,
    val content: String,
    val scrapCount: Long
) {
    companion object {
        fun toDto(board: Board): BoardDto =
            BoardDto(
                boardId = board.id!!,
                title = board.title,
                content = board.content,
                scrapCount = board.scrapCount
            )
    }
}