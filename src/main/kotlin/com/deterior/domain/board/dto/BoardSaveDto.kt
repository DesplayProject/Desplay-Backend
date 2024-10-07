package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType

data class BoardSaveDto(
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>
)