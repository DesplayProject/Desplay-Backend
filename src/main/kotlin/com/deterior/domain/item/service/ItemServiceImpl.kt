package com.deterior.domain.item.service

import com.deterior.domain.board.Board
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
    override fun saveItem(itemSaveDtos: List<ItemSaveDto>): List<ItemDto> {
        val results = mutableListOf<ItemDto>()
        for (item in itemSaveDtos) {
            val board = findBoard(item)
            val savedItem = itemRepository.save(
                Item(
                    title = item.title,
                    link = item.link,
                    board = board,
                )
            )
            results.add(savedItem.toDto(board.toDto()))
        }
        return results
    }

    private fun findBoard(itemSaveDto: ItemSaveDto): Board = boardRepository.findById(itemSaveDto.boardDto.boardId).orElseThrow{ NoSuchElementException() }
}