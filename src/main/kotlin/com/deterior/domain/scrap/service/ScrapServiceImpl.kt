package com.deterior.domain.scrap.service

import com.deterior.domain.scrap.Scrap
import com.deterior.domain.scrap.dto.ScrapDto
import com.deterior.domain.scrap.dto.ScrapHandleDto
import com.deterior.domain.scrap.repository.ScrapRepository
import com.deterior.domain.board.repository.BoardRepository
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
    override fun doLike(scrapHandleDto: ScrapHandleDto): ScrapDto =
        scrapRepository.isScrapExists(scrapHandleDto)
            .let { if (it) return undoLike(scrapHandleDto) else return pushLike(scrapHandleDto) }

    @Transactional
    override fun isLike(scrapHandleDto: ScrapHandleDto): Boolean {
        return scrapRepository.isScrapExists(scrapHandleDto)
    }

    private fun pushLike(scrapHandleDto: ScrapHandleDto): ScrapDto {
        val board = boardRepository.findById(scrapHandleDto.boardId).get()
        val member = memberRepository.findByUsername(scrapHandleDto.username)
        val scrap = scrapRepository.save(
            Scrap(
                board = board,
                member = member!!
            )
        )
        return scrap.toDto()
    }

    private fun undoLike(scrapHandleDto: ScrapHandleDto): ScrapDto {
        val scrap = scrapRepository.findScrapByUsernameAndBoardId(scrapHandleDto)
        scrap.board.scrapCount--
        scrapRepository.deleteById(scrap.id!!)
        return scrap.toDto()
    }
}