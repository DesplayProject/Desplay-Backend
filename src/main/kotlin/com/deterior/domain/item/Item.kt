package com.deterior.domain.item

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.Board
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Item(
    var title: String,

    val link: String,

    @ManyToOne
    val board: Board
) : BaseEntity() {
    init {
        board.items.add(this)
    }
}