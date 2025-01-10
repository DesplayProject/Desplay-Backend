package com.deterior.global.repository

import com.deterior.global.Log
import org.springframework.data.mongodb.repository.MongoRepository

interface LogRepository : MongoRepository<Log, String> {
}