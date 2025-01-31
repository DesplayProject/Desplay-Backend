package com.deterior.domain.scrap.dto

import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.member.dto.MemberDto

data class ScrapDto(
    val memberDto: MemberDto,
    val boardDto: BoardDto
) {
    fun toResponse() = ScrapResponse(
        scrapCount = boardDto.scrapCount,
        username = memberDto.username,
        title = boardDto.title,
    )
}