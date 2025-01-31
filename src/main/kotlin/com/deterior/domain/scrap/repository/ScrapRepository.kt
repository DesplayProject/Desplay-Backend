package com.deterior.domain.scrap.repository

import com.deterior.domain.scrap.Scrap
import org.springframework.data.jpa.repository.JpaRepository

interface ScrapRepository : JpaRepository<Scrap, Long>, ScrapSearchRepository {
}