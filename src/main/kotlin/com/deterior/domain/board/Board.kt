package com.deterior.domain.board

import com.deterior.domain.BaseEntity
import com.deterior.domain.item.Item
import jakarta.persistence.*

@Entity
class Board(
    val title: String,

    val content: String,

    @ElementCollection(fetch = FetchType.EAGER)
    val moodTypes: List<MoodType> = mutableListOf(),

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: List<Item> = mutableListOf(),
) : BaseEntity() {
}
