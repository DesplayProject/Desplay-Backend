package com.deterior.domain.board.dto

import com.deterior.domain.board.Board
import com.deterior.domain.board.MoodType
import com.deterior.domain.member.Member
import com.deterior.domain.member.dto.MemberDto
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor

data class BoardDto(
    val boardId: Long,
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>,
    val scrapCount: Long
) {
    companion object {
        fun toDto(board: Board): BoardDto =
            BoardDto(
                boardId = board.id!!,
                title = board.title,
                content = board.content,
                moodTypes = board.moodTypes,
                scrapCount = board.scrapCount
            )
    }
}