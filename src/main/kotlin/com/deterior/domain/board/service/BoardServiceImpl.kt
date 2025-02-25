package com.deterior.domain.board.service

import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.board.repository.SearchType.*
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.global.util.ObjectSerializer
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
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

    @Transactional
    override fun selectSearch(condition: BoardSearchCondition, pageable: Pageable): Page<BoardFindDto> {
        when (condition.searchType) {
            MAIN -> {
                val content = boardRepository.mainSearch(condition, pageable)
                return PageImpl(content, pageable, content.size.toLong())
            }
            MY_LIKE -> {
                val content = boardRepository.myLikeSearch(condition, pageable)
                return PageImpl(content, pageable, content.size.toLong())
            }
            MY_WRITE -> {
                val content = boardRepository.myWriteSearch(condition, pageable)
                return PageImpl(content, pageable, content.size.toLong())
            }
        }
    }

    @Transactional
    override fun getBoardDetail(boardId: Long): BoardFindDto = boardRepository.getBoard(boardId)
}