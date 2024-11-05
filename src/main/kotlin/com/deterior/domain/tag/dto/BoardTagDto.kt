package com.deterior.domain.tag.dto

import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.tag.BoardTag

class BoardTagDto(
    val boardTagId: Long,
    val boardDto: BoardDto,
    val tagDto: TagDto
)