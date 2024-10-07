package com.deterior.domain.image.dto

import com.deterior.domain.board.Board
import com.deterior.domain.board.BoardDto
import org.springframework.web.multipart.MultipartFile

data class FileUploadDto(
    val files: List<MultipartFile>,
    val boardDto: BoardDto,
) {

}