package com.deterior.domain.scrap.repository

import com.deterior.domain.scrap.Scrap
import com.deterior.domain.scrap.dto.ScrapHandleDto

interface ScrapSearchRepository {
    fun findScrapByUsernameAndBoardId(scrapHandleDto: ScrapHandleDto): Scrap
}