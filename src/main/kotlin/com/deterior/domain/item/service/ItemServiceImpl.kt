package com.deterior.domain.item.service

import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.item.Item
import com.deterior.domain.item.dto.ItemDto
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.repository.ItemRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl @Autowired constructor(
    private val itemRepository: ItemRepository,
    private val boardRepository: BoardRepository,
) : ItemService {
    @Transactional
    override fun saveItem(itemSaveDto: ItemSaveDto): List<ItemDto> {
        val board = boardRepository.findById(itemSaveDto.boardDto.boardId).orElseThrow{ NoSuchElementException() }
        val results = mutableListOf<ItemDto>()
        for (item in itemSaveDto.items) {
            val savedItem = itemRepository.save(
                Item(
                    title = item.first,
                    link = item.second,
                    board = board,
                )
            )
            results.add(ItemDto.toDto(savedItem, board))
        }
        return results
    }
}