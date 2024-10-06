package com.deterior.domain.item.dto.response

import com.deterior.domain.item.Item

data class ItemSaveResponse(
    val title: String,
) {
    companion object {
        fun toResponse(item: Item): ItemSaveResponse =
            ItemSaveResponse(
                title = item.name
            )
    }
}