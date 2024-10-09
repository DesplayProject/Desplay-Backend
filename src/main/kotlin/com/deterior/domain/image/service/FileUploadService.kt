package com.deterior.domain.image.service

import com.deterior.domain.image.ImageDto
import com.deterior.domain.image.dto.FileUploadDto

interface FileUploadService {
    fun saveFile(fileUploadDto: FileUploadDto): List<ImageDto>
}