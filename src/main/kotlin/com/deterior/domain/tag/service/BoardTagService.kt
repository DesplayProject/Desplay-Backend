package com.deterior.domain.tag.service

import com.deterior.domain.tag.dto.BoardTagDto
import com.deterior.domain.tag.dto.BoardTagSaveDto

interface BoardTagService {
    fun saveTag(boardTagSaveDtos: List<BoardTagSaveDto>): List<BoardTagDto>
}