package com.deterior.domain.item.dto

import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.item.Item

data class ItemSaveDto(
    val items: List<Pair<String, String>>,
    val boardDto: BoardDto
)