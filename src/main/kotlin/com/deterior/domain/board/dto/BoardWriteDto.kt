package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType
import com.deterior.domain.member.dto.MemberDto
import org.springframework.web.multipart.MultipartFile

data class BoardWriteDto(
    val title: String,
    val content: String,
    val moodTypes: List<MoodType>,
    val files: List<MultipartFile>,
    val items: List<Pair<String, String>>,
    val memberDto: MemberDto,
) {
    companion object {
        fun toDto(boardWriteRequest: BoardWriteRequest, files: List<MultipartFile>, memberDto: MemberDto): BoardWriteDto =
            BoardWriteDto(
                title = boardWriteRequest.title,
                content = boardWriteRequest.content,
                moodTypes = boardWriteRequest.moodTypes,
                files = files,
                items = boardWriteRequest.items,
                memberDto = memberDto,
            )
    }
}