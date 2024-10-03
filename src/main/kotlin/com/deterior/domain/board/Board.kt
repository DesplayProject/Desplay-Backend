package com.deterior.domain.board

import com.deterior.domain.BaseEntity
import jakarta.persistence.*

@Entity
class Board(
    val title: String,

    val content: String,

    @ElementCollection(fetch = FetchType.EAGER)
    val moodTypes: List<MoodType> = mutableListOf(),

) : BaseEntity() {
}
