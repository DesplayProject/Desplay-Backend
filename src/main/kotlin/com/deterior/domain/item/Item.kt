package com.deterior.domain.item

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.Board
import com.deterior.domain.item.dto.ItemSaveDto
import com.deterior.domain.item.dto.request.ItemSaveRequest
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Item(
    var title: String,

    @ManyToOne
    val board: Board
) : BaseEntity() {
    init {
        board.items.add(this)
    }
}