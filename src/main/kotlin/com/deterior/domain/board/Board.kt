package com.deterior.domain.board

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.image.Image
import com.deterior.domain.item.Item
import com.deterior.domain.member.Member
import jakarta.persistence.*

@Entity
class Board(
    val title: String,

    val content: String,

    @ElementCollection(fetch = FetchType.EAGER)
    val moodTypes: List<MoodType> = mutableListOf(),

    @ManyToOne
    val member: Member
) : BaseEntity() {
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<Item> = mutableListOf()

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val images: MutableList<Image> = mutableListOf()

    fun toDto(): BoardDto = BoardDto(
        boardId = id!!,
        title = title,
        content = content,
        moodTypes = moodTypes,
    )
}
