package com.deterior.domain.image.service

import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.image.Image
import com.deterior.domain.image.dto.ImageDto
import com.deterior.domain.image.dto.FileSaveDto
import com.deterior.domain.image.repository.ImageRepository
import com.deterior.global.exception.ErrorCode
import com.deterior.global.exception.ImageNotSupportedTypeException
import com.deterior.global.util.ApplicationProperties
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.util.*
import kotlin.NoSuchElementException

@Service
class DBFileUploadService @Autowired constructor(
    val imageRepository: ImageRepository,
    val boardRepository: BoardRepository,
    val applicationProperties: ApplicationProperties
) : FileUploadService{

    @Transactional
    override fun saveFile(fileSaveDto: FileSaveDto): List<ImageDto> {
        val board = boardRepository.findById(fileSaveDto.boardDto.boardId).orElseThrow{ NoSuchElementException() }
        val results = mutableListOf<ImageDto>()
        for (image in fileSaveDto.files) {
            val originFileName: String? = image.originalFilename
            val saveFileName = createSaveFileName(originFileName)
            val filePath = getFilePath(saveFileName)
            image.transferTo(File(filePath))
            val savedImage = imageRepository.save(
                Image(
                    originFileName = originFileName,
                    saveFileName = saveFileName,
                    board = board,
                )
            )
            results.add(savedImage.toDto(board.toDto()))
        }
        return results
    }

    private fun createSaveFileName(fileName: String?): String {
        val ext = extractExt(fileName)
        val fileId: String = UUID.randomUUID().toString()
        return "${fileId}.${ext}"
    }

    private fun extractExt(fileName: String?): String? {
        val idx = fileName?.lastIndexOf('.')
        fileName?.substring(idx?.plus(1) as Int)
            .let {
                if (it == "jpg" || it == "png" || it == "jpeg") return it
                else throw ImageNotSupportedTypeException(
                message = ErrorCode.NOT_SUPPORTED_TYPE.message,
                value = it!!,
                errorCode = ErrorCode.NOT_SUPPORTED_TYPE
            )
            }
    }

    private fun getFilePath(fileName: String?): String {
        return "${applicationProperties.upload.path}${fileName}"
    }
}