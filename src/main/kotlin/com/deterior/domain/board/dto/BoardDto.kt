package com.deterior.domain.board.dto

import com.deterior.domain.board.Board
import com.deterior.domain.board.MoodType
import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.MemberDto

data class BoardDto(
    val boardId: Long,
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>,
) {
    companion object {
        fun toDto(board: Board): BoardDto =
            BoardDto(
                boardId = board.id!!,
                title = board.title,
                content = board.content,
                moodTypes = board.moodTypes,
            )
    }
}