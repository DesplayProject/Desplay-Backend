package com.deterior.domain.board.dto

import com.deterior.domain.board.BoardDto
import com.deterior.domain.board.MoodType
import com.deterior.domain.board.dto.request.BoardWriteRequest

data class BoardSaveDto(
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>
) {
    companion object {
        fun toDto(boardWriteRequest: BoardWriteRequest): BoardSaveDto =
            BoardSaveDto(
                title = boardWriteRequest.title,
                content = boardWriteRequest.content,
                moodTypes = boardWriteRequest.moodTypes
            )
    }
}