package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType
import com.deterior.domain.image.dto.ImageDto
import com.deterior.domain.item.dto.ItemDto
import com.querydsl.core.annotations.QueryProjection

data class BoardSearchDto @QueryProjection constructor(
    val boardId: Long,
)