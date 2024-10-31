package com.deterior.domain.board.dto

import com.deterior.domain.board.MoodType
import com.deterior.domain.image.dto.ImageFindDto
import com.deterior.domain.item.dto.ItemDto
import com.deterior.domain.item.dto.ItemFindDto

data class BoardFindDto(
    val boardId: Long,
    val title: String,
    val moodTypes: List<MoodType>,
    val username: String,
    val items: List<ItemFindDto>,
    val images: List<ImageFindDto>
)