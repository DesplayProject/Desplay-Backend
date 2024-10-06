package com.deterior.domain.item.service

import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.item.Item
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.dto.request.ItemSaveRequest
import com.deterior.domain.item.dto.response.ItemSaveResponse
import com.deterior.domain.item.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl @Autowired constructor(
    val itemRepository: ItemRepository,
    val boardRepository: BoardRepository
) : ItemService {
    override fun saveItem(itemSaveDto: ItemSaveDto): ItemSaveResponse {
        val board = boardRepository.findById(itemSaveDto.boardId).orElseThrow()
        val item = itemRepository.save(Item.toEntity(itemSaveDto.title, board))
        return ItemSaveResponse.toResponse(item)
    }
}