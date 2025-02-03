package com.deterior.domain.scrap.service

import com.deterior.domain.scrap.dto.ScrapDto
import com.deterior.domain.scrap.dto.ScrapHandleDto

interface ScrapService {
//    fun pushLike(scrapHandleDto: ScrapHandleDto): ScrapDto
//    fun undoLike(scrapHandleDto: ScrapHandleDto): ScrapDto
    fun doLike(scrapHandleDto: ScrapHandleDto): ScrapDto
}