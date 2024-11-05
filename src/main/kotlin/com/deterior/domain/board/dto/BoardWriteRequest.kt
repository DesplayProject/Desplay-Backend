package com.deterior.domain.board.dto

import com.deterior.domain.member.dto.MemberContext
import org.springframework.web.multipart.MultipartFile

data class BoardWriteRequest(
    val title: String,
    val content: String,
    val tags: List<String>,
    val items: List<Pair<String, String>>
) {
    fun toWriteDto(
        files: List<MultipartFile>,
        memberContext: MemberContext
    ): BoardWriteDto = BoardWriteDto(
        title = title,
        content = content,
        tags = tags,
        files = files,
        items = items,
        memberDto = memberContext.memberDto
    )
}