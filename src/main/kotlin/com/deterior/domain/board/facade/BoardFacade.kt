package com.deterior.domain.board.facade

import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.dto.BoardWriteDto
import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.service.BoardService
import com.deterior.domain.image.dto.FileUploadDto
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
        val boardDto = boardService.saveBoard(BoardSaveDto.toDto(boardWriteDto))
        fileUploadService.saveFile(
            FileUploadDto(
                files = boardWriteDto.files,
                boardDto = boardDto
            )
        )
        itemService.saveItem(
            ItemSaveDto(
                items = boardWriteDto.items,
                boardDto = boardDto
            )
        )
    }
}