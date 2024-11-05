package com.deterior.domain.board

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.image.Image
import com.deterior.domain.item.Item
import com.deterior.domain.member.Member
import com.deterior.domain.tag.BoardTag
import jakarta.persistence.*

@Entity
class Board(
    val title: String,

    val content: String,

    @ManyToOne
    val member: Member
) : BaseEntity() {
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val items: MutableList<Item> = mutableListOf()

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val images: MutableList<Image> = mutableListOf()

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val tags: MutableList<BoardTag> = mutableListOf()

    var scrapCount: Long = 0

    fun toDto(): BoardDto = BoardDto(
        boardId = id!!,
        title = title,
        content = content,
        scrapCount = scrapCount,
    )
}
