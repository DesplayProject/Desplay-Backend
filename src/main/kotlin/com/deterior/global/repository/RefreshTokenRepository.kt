package com.deterior.global.repository

import com.deterior.global.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
}