package com.deterior.domain.board.repository

import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.dto.BoardMainFindDto
import com.deterior.domain.board.dto.BoardMemberFindDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardSearchRepository {
    fun mainBoardSearch(condition: BoardSearchCondition?, pageable: Pageable): Page<BoardMainFindDto>
    fun memberBoardSearch(condition: BoardSearchCondition?, pageable: Pageable): Page<BoardMemberFindDto>
}