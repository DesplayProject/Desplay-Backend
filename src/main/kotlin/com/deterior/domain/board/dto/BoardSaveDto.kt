package com.deterior.domain.board.dto

import com.deterior.domain.board.Board
import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.tag.dto.TagDto

data class BoardSaveDto(
    val title: String,
    val content: String,
    val tags: List<String>,
    val memberDto: MemberDto
) {
    fun toEntity(member: Member): Board = Board(
        title = title,
        content = content,
        member = member
    )
}