package com.deterior.domain.scrap

import com.deterior.domain.BaseEntity
import com.deterior.domain.scrap.dto.ScrapDto
import com.deterior.domain.board.Board
import com.deterior.domain.member.Member
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Scrap(
    @ManyToOne
    val member: Member,

    @ManyToOne
    val board: Board,
) : BaseEntity() {
    fun toDto() = ScrapDto(
        memberDto = member.toDto(),
        boardDto = board.toDto(),
    )
}