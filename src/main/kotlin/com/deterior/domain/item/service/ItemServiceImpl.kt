package com.deterior.domain.item.service

import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.item.Item
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.dto.request.ItemSaveRequest
import com.deterior.domain.item.repository.ItemRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl @Autowired constructor(
    val itemRepository: ItemRepository,
    val boardRepository: BoardRepository
) : ItemService {
    @Transactional
    override fun saveItem(itemSaveDto: ItemSaveDto) {
        val board = boardRepository.findById(itemSaveDto.boardDto.boardId).orElseThrow{ NoSuchElementException() }
        for (title in itemSaveDto.items) {
            itemRepository.save(
                Item(
                    title,
                    board,
                )
            )
        }
    }
}