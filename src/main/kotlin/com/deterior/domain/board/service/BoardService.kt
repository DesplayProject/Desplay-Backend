package com.deterior.domain.board.service

import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.dto.BoardSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun saveBoard(boardSaveDto: BoardSaveDto): BoardDto
    fun findBoardById(id: Long): BoardDto
    fun selectSearch(condition: BoardSearchCondition, pageable: Pageable): Page<BoardFindDto>
}