package com.deterior.domain.item.dto

import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.item.Item

data class ItemDto(
    val id: Long,
    val title: String,
    val link: String,
    val boardDto: BoardDto,
) {
    companion object {
        fun toDto(item: Item, board: Board): ItemDto =
            ItemDto(
                id = item.id!!,
                title = item.title,
                link = item.link,
                boardDto = BoardDto.toDto(board)
            )
    }
}