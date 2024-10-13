package com.deterior.domain.item.service

import com.deterior.domain.item.dto.ItemDto
import com.deterior.domain.item.dto.ItemSaveDto

interface ItemService {
    fun saveItem(itemSaveDtos: List<ItemSaveDto>): List<ItemDto>
}