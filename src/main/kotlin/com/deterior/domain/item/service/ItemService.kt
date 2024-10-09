package com.deterior.domain.item.service

import com.deterior.domain.item.ItemDto
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.dto.request.ItemSaveRequest

interface ItemService {
    fun saveItem(itemSaveDto: ItemSaveDto): List<ItemDto>
}