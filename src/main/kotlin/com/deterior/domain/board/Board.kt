package com.deterior.domain.board

import com.deterior.domain.BaseEntity
import com.deterior.domain.board.dto.BoardDto
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSaveDto
import com.deterior.domain.image.Image
import com.deterior.domain.image.dto.ImageFindDto
import com.deterior.domain.item.Item
import com.deterior.domain.item.dto.ItemFindDto
import com.deterior.domain.member.Member
import com.deterior.domain.tag.BoardTag
import com.deterior.domain.tag.dto.TagFindDto
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

    fun toFindDto(): BoardFindDto = BoardFindDto(
        boardId = id!!,
        title = title,
        scrapCount = scrapCount,
        username = member.username,
        tags = tags.map { it -> TagFindDto(
            tagId = it.tag.id!!,
            title = it.tag.title
        ) },
        items = items.map { it -> ItemFindDto(
            itemId = it.id!!,
            title = it.title,
            link = it.link,
        ) },
        images = images.map { it -> ImageFindDto(
            imageId = it.id!!,
            saveFileName = it.saveFileName,
        ) }
    )
}
