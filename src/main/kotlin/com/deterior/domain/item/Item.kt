package com.deterior.domain.item

import com.deterior.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Item(
    var name: String,
    var price: Int,
) : BaseEntity() {
}