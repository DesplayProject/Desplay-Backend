package com.deterior.global.exception.dto

import com.deterior.global.exception.ErrorCode

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