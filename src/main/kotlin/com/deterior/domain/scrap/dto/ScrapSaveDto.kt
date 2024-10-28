package com.deterior.domain.scrap.dto

import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.member.dto.MemberDto

data class ScrapSaveDto(
    val memberDto: MemberDto,
    val boardDto: BoardDto
)