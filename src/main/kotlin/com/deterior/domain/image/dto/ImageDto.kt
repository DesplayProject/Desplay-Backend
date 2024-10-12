package com.deterior.domain.image.dto

import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.image.Image

data class ImageDto(
    val imageId: Long,
    val originFileName: String,
    val saveFileName: String,
    val boardDto: BoardDto
) {
}