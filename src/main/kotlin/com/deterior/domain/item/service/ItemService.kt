package com.deterior.domain.item.service

import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.dto.request.ItemSaveRequest
import com.deterior.domain.item.dto.response.ItemSaveResponse

interface ItemService {
    fun saveItem(itemSaveDto: ItemSaveDto)
}