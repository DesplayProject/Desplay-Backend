package com.deterior.global.exception.dto

import org.springframework.http.HttpStatus
import java.lang.Exception

class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String,
    val values: List<String>
) {
    companion object {
        fun toResponse(status: Int, errorCode: ErrorCode, values: List<String>): ErrorResponse =
            ErrorResponse(
                status = status,
                code = errorCode.code,
                message = errorCode.message,
                values = values
            )
    }
}