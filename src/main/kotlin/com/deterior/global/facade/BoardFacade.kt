package com.deterior.global.facade

import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.dto.request.BoardWriteRequest
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
    val boardService: BoardService,
    val fileUploadService: FileUploadService,
    val itemService: ItemService,
) {

    @Transactional
    fun writeBoard(boardWriteRequest: BoardWriteRequest) {
        val boardDto = boardService.saveBoard(BoardSaveDto.toDto(boardWriteRequest))
        fileUploadService.saveFile(
            FileUploadDto(
                files = boardWriteRequest.files,
                boardDto = boardDto
            )
        )
        itemService.saveItem(
            ItemSaveDto(
                items = boardWriteRequest.items,
                boardDto = boardDto
            )
        )
    }
}