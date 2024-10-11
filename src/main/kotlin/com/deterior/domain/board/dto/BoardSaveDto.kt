package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType
import com.deterior.domain.member.dto.MemberDto

data class BoardSaveDto(
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>,
    val memberDto: MemberDto
) {
    companion object {
        fun toDto(boardWriteDto: BoardWriteDto): BoardSaveDto =
            BoardSaveDto(
                title = boardWriteDto.title,
                content = boardWriteDto.content,
                moodTypes = boardWriteDto.moodTypes,
                memberDto = boardWriteDto.memberDto
            )
    }
}