package com.deterior.domain.board.dto

import com.deterior.domain.image.dto.ImageFindDto
import com.deterior.domain.item.dto.ItemFindDto
import com.deterior.domain.tag.dto.TagDto
import com.deterior.domain.tag.dto.TagFindDto

data class BoardFindDto(
    val boardId: Long,
    val title: String,
    val scrapCount: Long,
    val username: String,
    val tags: List<TagFindDto>,
    val items: List<ItemFindDto>,
    val images: List<ImageFindDto>
)