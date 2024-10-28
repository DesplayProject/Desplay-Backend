package com.deterior.domain.scrap.service

import com.deterior.domain.scrap.Scrap
import com.deterior.domain.scrap.dto.ScrapDto
import com.deterior.domain.scrap.dto.ScrapSaveDto
import com.deterior.domain.scrap.dto.ScrapUndoDto
import com.deterior.domain.scrap.repository.ScrapRepository
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScrapServiceImpl @Autowired constructor(
    val scrapRepository: ScrapRepository,
    val boardRepository: BoardRepository,
    val memberRepository: MemberRepository
) : ScrapService {

    @Transactional
    override fun pushLike(likeSaveDto: ScrapSaveDto): ScrapDto {
        val board = findBoard(likeSaveDto.boardDto)
        val member = findMember(likeSaveDto.memberDto)
        val scrap = scrapRepository.save(
            Scrap(
                board = board,
                member = member
            )
        )
        return scrap.toDto()
    }

    @Transactional
    override fun undoLike(scrapUndoDto: ScrapUndoDto) {
        scrapRepository.deleteById(scrapUndoDto.scrapId)
    }

    private fun findBoard(boardDto: BoardDto) = boardRepository.findById(boardDto.boardId).get()

    private fun findMember(memberDto: MemberDto) = memberRepository.findById(memberDto.memberId).get()
}