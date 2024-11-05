package com.deterior.domain.tag.service

import com.deterior.domain.board.Board
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.tag.BoardTag
import com.deterior.domain.tag.Tag
import com.deterior.domain.tag.dto.BoardTagDto
import com.deterior.domain.tag.dto.BoardTagSaveDto
import com.deterior.domain.tag.repository.BoardTagRepository
import com.deterior.domain.tag.repository.TagRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BoardTagServiceImpl @Autowired constructor (
    private val boardTagRepository: BoardTagRepository,
    private val tageRepository: TagRepository,
    private val boardRepository: BoardRepository
) : BoardTagService {

    @Transactional
    override fun saveTag(boardTagSaveDtos: List<BoardTagSaveDto>): List<BoardTagDto> {
        val result = mutableListOf<BoardTagDto>()
        for (boardTag in boardTagSaveDtos) {
            val board = findBoard(boardTag)
            var savedTag: Tag?
            if (!tageRepository.existsByTitle(boardTag.title)) {
                savedTag = tageRepository.save(
                    Tag(
                        title = boardTag.title,
                    ))
            } else {
                savedTag = tageRepository.findByTitle(boardTag.title).get()
            }
            val savedBoardTag = boardTagRepository.save(
                BoardTag(
                    board = board,
                    tag = savedTag
                ))
            result.add(savedBoardTag.toDto())
        }
        return result
    }

    private fun findBoard(boardTagSaveDto: BoardTagSaveDto) = boardRepository.findById(boardTagSaveDto.boardDto.boardId).get()
}