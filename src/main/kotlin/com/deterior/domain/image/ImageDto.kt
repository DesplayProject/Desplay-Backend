package com.deterior.domain.image

import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto

data class ImageDto(
    val id: Long,
    val originFileName: String,
    val saveFileName: String,
    val boardDto: BoardDto
) {
    companion object {
        fun toDto(image: Image, board: Board): ImageDto =
            ImageDto(
                id = image.id!!,
                originFileName = image.originFileName!!,
                saveFileName = image.saveFileName,
                boardDto = BoardDto.toDto(board)
            )
    }
}