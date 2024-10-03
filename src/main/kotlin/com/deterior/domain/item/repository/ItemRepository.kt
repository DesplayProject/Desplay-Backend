package com.deterior.domain.item.repository

import com.deterior.domain.item.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long> {
}