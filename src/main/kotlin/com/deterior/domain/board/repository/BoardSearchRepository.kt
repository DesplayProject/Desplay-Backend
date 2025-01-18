package com.deterior.domain.board.repository

import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardSearchRepository {
    fun mainSearch(condition: BoardSearchCondition, pageable: Pageable): List<BoardFindDto>
    fun myLikeSearch(condition: BoardSearchCondition, pageable: Pageable): List<BoardFindDto>
    fun myWriteSearch(condition: BoardSearchCondition, pageable: Pageable): List<BoardFindDto>
}