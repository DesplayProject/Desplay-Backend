package com.deterior.domain.item.dto

import com.deterior.domain.board.Board
import com.deterior.domain.board.BoardDto

data class ItemSaveDto(
    val items: List<Pair<String, String>>,
    val boardDto: BoardDto
)