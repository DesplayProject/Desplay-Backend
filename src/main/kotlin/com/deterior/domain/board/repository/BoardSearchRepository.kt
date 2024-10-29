package com.deterior.domain.board.repository

import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.dto.BoardSearchDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardSearchRepository {
    fun search(condition: BoardSearchCondition?, pageable: Pageable): Page<BoardSearchDto>
}