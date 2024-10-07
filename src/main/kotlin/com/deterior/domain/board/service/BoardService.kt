package com.deterior.domain.board.service

import com.deterior.domain.board.dto.BoardSaveDto

interface BoardService {
    fun saveBoard(boardSaveDto: BoardSaveDto)
}