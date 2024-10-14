package com.deterior.sercurity.repository

import com.deterior.sercurity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
}