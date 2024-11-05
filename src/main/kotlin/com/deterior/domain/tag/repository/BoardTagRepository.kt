package com.deterior.domain.tag.repository

import com.deterior.domain.tag.BoardTag
import org.springframework.data.jpa.repository.JpaRepository

interface BoardTagRepository: JpaRepository<BoardTag, Long> {
}