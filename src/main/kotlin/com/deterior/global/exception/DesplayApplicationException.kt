package com.deterior.global.exception

open class DesplayApplicationException(
    override val message: String,
    open val value: String,
    open val errorCode: ErrorCode
) : RuntimeException()