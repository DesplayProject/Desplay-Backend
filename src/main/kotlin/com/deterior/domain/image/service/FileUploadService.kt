package com.deterior.domain.image.service

import com.deterior.domain.image.dto.ImageDto
import com.deterior.domain.image.dto.FileSaveDto

interface FileUploadService {
    fun saveFile(fileSaveDto: FileSaveDto): List<ImageDto>
}