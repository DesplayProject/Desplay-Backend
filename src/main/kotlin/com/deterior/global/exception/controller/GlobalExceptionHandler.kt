package com.deterior.global.exception.controller

import com.deterior.global.exception.AuthenticationFailException
import com.deterior.global.exception.DuplicateException
import com.deterior.global.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateException::class)
    fun handleDuplicateException(
        exception: DuplicateException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.toResponse(
            status = HttpStatus.CONFLICT.value(),
            errorCode = exception.errorCode,
            values = listOf(exception.value)
        )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AuthenticationFailException::class)
    fun handleAuthenticationFailException(
        exception: AuthenticationFailException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.toResponse(
            status = HttpStatus.UNAUTHORIZED.value(),
            errorCode = exception.errorCode,
            values = listOf(exception.value)
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }
}