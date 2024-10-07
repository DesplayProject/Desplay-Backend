package com.deterior.domain.image.dto

import com.deterior.domain.board.Board
import org.springframework.web.multipart.MultipartFile

data class FileUploadDto(
    val board: Board,
    val files: List<MultipartFile>
)