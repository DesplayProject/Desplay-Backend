package com.deterior.domain.tag.dto

import com.deterior.domain.board.dto.BoardDto

data class BoardTagSaveDto(
    val title: String,
    val boardDto: BoardDto
)