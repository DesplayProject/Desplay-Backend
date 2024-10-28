package com.deterior.global.exception

open class AuthenticationFailException(
    override val message: String,
    override val value: String,
    override val errorCode: ErrorCode
) : DesplayApplicationException(message = message, value = value, errorCode = errorCode)

class EmailAuthenticationFailException(
    override val value: String,
    override val message: String,
    override val errorCode: ErrorCode
) : AuthenticationFailException(message = message, value = value, errorCode = errorCode)