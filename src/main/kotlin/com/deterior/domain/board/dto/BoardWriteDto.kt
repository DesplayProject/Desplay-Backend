package com.deterior.domain.board.dto

import com.deterior.domain.image.dto.FileSaveDto
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.member.dto.MemberDto
import com.deterior.domain.tag.dto.BoardTagSaveDto
import org.springframework.web.multipart.MultipartFile

data class BoardWriteDto(
    val title: String,
    val content: String,
    val tags: List<String>,
    val files: List<MultipartFile>,
    val items: List<Pair<String, String>>,
    val memberDto: MemberDto,
) {
    fun toBoardSaveDto(): BoardSaveDto = BoardSaveDto(
        title = title,
        content = content,
        tags = tags,
        memberDto = memberDto,
    )

    fun toFileSaveDto(boardDto: BoardDto): FileSaveDto = FileSaveDto(
        files = files,
        boardDto = boardDto,
    )

    fun toItemSaveDto(boardDto: BoardDto): List<ItemSaveDto> {
        val result = mutableListOf<ItemSaveDto>()
        for (item in items) {
            val itemSaveDto = ItemSaveDto(
                title = item.first,
                link = item.second,
                boardDto = boardDto,
            )
            result.add(itemSaveDto)
        }
        return result
    }

    fun toTagSaveDto(boardDto: BoardDto): List<BoardTagSaveDto> {
        val result = mutableListOf<BoardTagSaveDto>()
        for (tag in tags) {
            val boardTagSaveDto = BoardTagSaveDto(
                title = tag,
                boardDto = boardDto,
            )
            result.add(boardTagSaveDto)
        }
        return result
    }
}