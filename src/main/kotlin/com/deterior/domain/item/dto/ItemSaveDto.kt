package com.deterior.domain.item.dto

import com.deterior.domain.board.Board

data class ItemSaveDto(
    val title: String,
    val board: Board
)