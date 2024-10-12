package com.deterior.domain.board.facade

import com.deterior.domain.board.dto.BoardWriteDto
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
    fun writeBoard(boardWriteDto: BoardWriteDto) {
        val boardDto = boardService.saveBoard(boardWriteDto.toBoardSaveDto())
        fileUploadService.saveFile(boardWriteDto.toFileSaveDto(boardDto))
        itemService.saveItem(boardWriteDto.toItemSaveDto(boardDto))
    }
}