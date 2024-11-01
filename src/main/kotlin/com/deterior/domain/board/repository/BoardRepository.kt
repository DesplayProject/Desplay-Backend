package com.deterior.domain.board.repository

import com.deterior.domain.board.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface BoardRepository : JpaRepository<Board, Long>, BoardSearchRepository {
}