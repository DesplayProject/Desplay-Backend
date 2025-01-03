package com.deterior.domain.board.service

import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.global.util.ObjectSerializer
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository,
) : BoardService {

    @Transactional
    override fun saveBoard(boardSaveDto: BoardSaveDto): BoardDto {
        val member = memberRepository.findById(boardSaveDto.memberDto.memberId).orElseThrow{ NoSuchElementException() }
        val board = boardRepository.save(boardSaveDto.toEntity(member))
        val result = board.toDto()
        return result
    }

    @Transactional
    override fun findBoardById(id: Long): BoardDto {
        val board = boardRepository.findById(id).orElseThrow { NoSuchElementException() }
        val result = board.toDto()
        return result
    }
}