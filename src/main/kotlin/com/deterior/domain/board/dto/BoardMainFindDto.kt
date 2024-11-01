package com.deterior.domain.board.dto

import com.deterior.domain.image.dto.ImageFindDto

data class BoardMainFindDto(
    val boardId: Long,
    val images: List<ImageFindDto>
)