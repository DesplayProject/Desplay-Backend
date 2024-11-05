package com.deterior.domain.tag

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.Board
import com.deterior.domain.tag.dto.TagDto
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Tag(
    val title: String
): BaseEntity() {
    fun toDto() = TagDto(
        title = title
    )
}