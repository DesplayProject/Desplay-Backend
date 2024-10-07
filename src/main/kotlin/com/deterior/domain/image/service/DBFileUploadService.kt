package com.deterior.domain.image.service

import com.deterior.domain.image.Image
import com.deterior.domain.image.dto.FileUploadDto
import com.deterior.domain.image.repository.ImageRepository
import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class DBFileUploadService @Autowired constructor(
    val imageRepository: ImageRepository,
    val applicationProperties: ApplicationProperties
) : FileUploadService{

    override fun saveFile(fileUploadDto: FileUploadDto) {
        for (image in fileUploadDto.files) {
            val originFileName: String? = image.originalFilename
            val saveFileName = createSaveFileName(originFileName)
            val filePath = getFilePath(saveFileName)
            image.transferTo(File(filePath))
            val image = Image(
                originFileName = originFileName,
                saveFileName = saveFileName,
                board = fileUploadDto.board,
            )
            imageRepository.save(image)
        }
    }

    private fun createSaveFileName(fileName: String?): String {
        val ext = extractExt(fileName)
        val fileId: String = UUID.randomUUID().toString()
        return "${fileId}.${ext}"
    }

    private fun extractExt(fileName: String?): String? {
        val idx = fileName?.lastIndexOf('.')
        return fileName?.substring(idx?.plus(1) as Int)
    }

    private fun getFilePath(fileName: String?): String {
        return "${applicationProperties.upload.path}${fileName}"
    }
}