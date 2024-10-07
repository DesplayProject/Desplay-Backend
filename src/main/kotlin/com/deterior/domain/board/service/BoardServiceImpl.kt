package com.deterior.domain.board.service

import com.deterior.domain.board.Board
import com.deterior.domain.board.BoardDto
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.repository.BoardRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl @Autowired constructor(
    val boardRepository: BoardRepository
) : BoardService {

    @Transactional
    override fun saveBoard(boardSaveDto: BoardSaveDto): BoardDto {
        val board = boardRepository.save(
            Board.toEntity(boardSaveDto)
        )
        return BoardDto.toDto(board)
    }
}