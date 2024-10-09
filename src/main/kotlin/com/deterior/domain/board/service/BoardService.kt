package com.deterior.domain.board.service

import com.deterior.domain.board.BoardDto
import com.deterior.domain.board.dto.BoardSaveDto

interface BoardService {
    fun saveBoard(boardSaveDto: BoardSaveDto): BoardDto
    fun findBoardById(id: Long): BoardDto
}