package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType
import com.deterior.domain.member.dto.MemberContext
import org.springframework.web.multipart.MultipartFile

data class BoardWriteRequest(
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>,
    val items: List<Pair<String, String>>
) {
    fun toWriteDto(
        files: List<MultipartFile>,
        memberContext: MemberContext
    ): BoardWriteDto = BoardWriteDto(
        title = title,
        content = content,
        moodTypes = moodTypes,
        files = files,
        items = items,
        memberDto = memberContext.memberDto
    )
}