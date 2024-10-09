package com.deterior.domain.item

import com.deterior.domain.board.Board
import com.deterior.domain.board.BoardDto

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