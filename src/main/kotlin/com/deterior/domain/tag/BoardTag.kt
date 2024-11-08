package com.deterior.domain.tag

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.Board
import com.deterior.domain.tag.dto.BoardTagDto
import com.deterior.domain.tag.dto.TagDto
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class BoardTag(
    @ManyToOne
    val board: Board,

    @ManyToOne
    val tag: Tag,
): BaseEntity() {
    init {
        board.tags.add(this)
    }

    fun toDto() = BoardTagDto(
        boardTagId = id!!,
        boardDto = board.toDto(),
        tagDto = tag.toDto()
    )
}