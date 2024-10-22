package com.deterior.domain.board.dto

import com.deterior.domain.member.dto.MemberDto

data class BoardWriteResponse(
    val memberDto: MemberDto,
    val title: String,
    val itemSize: Int,
    val fileSize: Int,
)