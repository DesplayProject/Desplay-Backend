package com.deterior.domain.board.service

import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.repository.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl @Autowired constructor(
    val boardRepository: BoardRepository
) : BoardService {
    override fun saveBoard(boardSaveDto: BoardSaveDto) {
        boardRepository.save(
            Board.toEntity(boardSaveDto)
        )
    }
}