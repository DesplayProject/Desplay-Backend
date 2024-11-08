package com.deterior.domain.tag.repository

import com.deterior.domain.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TagRepository: JpaRepository<Tag, Long> {
    fun existsByTitle(title: String): Boolean
    fun findByTitle(title: String): Optional<Tag>
}