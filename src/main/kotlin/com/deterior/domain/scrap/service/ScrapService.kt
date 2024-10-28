package com.deterior.domain.scrap.service

import com.deterior.domain.scrap.dto.ScrapDto
import com.deterior.domain.scrap.dto.ScrapSaveDto
import com.deterior.domain.scrap.dto.ScrapUndoDto

interface ScrapService {
    fun pushLike(scrapSaveDto: ScrapSaveDto): ScrapDto
    fun undoLike(scrapUndoDto: ScrapUndoDto)
}