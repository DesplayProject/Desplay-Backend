package com.deterior.global.exception

import com.deterior.global.exception.dto.ErrorCode

open class DuplicateException(
    override val message: String,
    open val value: String,
    open val errorCode: ErrorCode
) : RuntimeException()

class DuplicateUsernameException(
    override val value: String,
    override val message: String,
    override val errorCode: ErrorCode
) : DuplicateException(message = message, value = value, errorCode = errorCode)

class DuplicateEmailException(
    override val value: String,
    override val message: String,
    override val errorCode: ErrorCode
) : DuplicateException(message = message, value = value, errorCode = errorCode)