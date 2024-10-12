package com.deterior.domain.board.dto

import com.deterior.domain.board.Board
import com.deterior.domain.board.MoodType
import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.MemberDto

data class BoardSaveDto(
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>,
    val memberDto: MemberDto
) {
    fun toEntity(member: Member): Board = Board(
        title = title,
        content = content,
        moodTypes = moodTypes,
        member = member
    )
}