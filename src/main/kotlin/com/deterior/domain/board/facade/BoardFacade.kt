package com.deterior.domain.board.facade

import com.deterior.domain.board.dto.BoardWriteDto
import com.deterior.domain.board.dto.BoardWriteResponse
import com.deterior.domain.board.service.BoardService
import com.deterior.domain.image.dto.FileSaveDto
import com.deterior.domain.image.service.FileUploadService
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.service.ItemService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BoardFacade @Autowired constructor(
    private val boardService: BoardService,
    private val fileUploadService: FileUploadService,
    private val itemService: ItemService,
) {
    @Transactional
    fun writeBoard(boardWriteDto: BoardWriteDto): BoardWriteResponse {
        val boardDto = boardService.saveBoard(boardWriteDto.toBoardSaveDto())
        val saveFiles = fileUploadService.saveFile(boardWriteDto.toFileSaveDto(boardDto))
        val saveItems = itemService.saveItem(boardWriteDto.toItemSaveDto(boardDto))
        return BoardWriteResponse(
            memberDto = boardWriteDto.memberDto,
            title = boardWriteDto.title,
            itemSize = saveItems.size,
            fileSize = saveFiles.size,
        )
    }
}