package com.deterior.global.exception

open class DuplicateException(
    override val message: String,
    override val value: String,
    override val errorCode: ErrorCode
) : DesplayApplicationException(message = message, value = value, errorCode = errorCode)

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