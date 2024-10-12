package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType
import com.deterior.domain.image.dto.FileSaveDto
import com.deterior.domain.item.dto.ItemSaveDto
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
    fun toBoardSaveDto(): BoardSaveDto = BoardSaveDto(
        title = title,
        content = content,
        moodTypes = moodTypes,
        memberDto = memberDto,
    )

    fun toFileSaveDto(boardDto: BoardDto): FileSaveDto = FileSaveDto(
        files = files,
        boardDto = boardDto,
    )

    fun toItemSaveDto(boardDto: BoardDto): ItemSaveDto = ItemSaveDto(
        items = items,
        boardDto = boardDto,
    )
}