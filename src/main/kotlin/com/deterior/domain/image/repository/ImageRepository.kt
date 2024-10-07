package com.deterior.domain.image.repository

import com.deterior.domain.image.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long> {
}