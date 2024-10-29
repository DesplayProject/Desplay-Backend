package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType

data class BoardSearchCondition(
    val moodTypes: List<MoodType>?,
    val keyword: String?,
)