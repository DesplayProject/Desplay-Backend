package com.deterior.domain.image

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.image.dto.ImageDto
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Image(
    val originFileName: String?,

    val saveFileName: String,

    @ManyToOne
    val board: Board,
) : BaseEntity() {
    init {
        board.images.add(this)
    }

    fun toDto(boardDto: BoardDto): ImageDto = ImageDto(
        imageId = id!!,
        originFileName = originFileName!!,
        saveFileName = saveFileName,
        boardDto = boardDto,
    )
}