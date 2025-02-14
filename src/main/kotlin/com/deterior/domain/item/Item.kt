package com.deterior.domain.item

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.Board
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.item.dto.ItemDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Item(
    var title: String,

    @Column(length = 850)
    val link: String,

    @ManyToOne
    val board: Board
) : BaseEntity() {
    init {
        board.items.add(this)
    }

    fun toDto(boardDto: BoardDto): ItemDto = ItemDto(
        itemId = id!!,
        title = title,
        link = link,
        boardDto = boardDto
    )
}