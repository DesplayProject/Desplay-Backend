package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType
import org.springframework.web.multipart.MultipartFile

data class BoardWriteRequest(
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>,
    val items: List<Pair<String, String>>
)