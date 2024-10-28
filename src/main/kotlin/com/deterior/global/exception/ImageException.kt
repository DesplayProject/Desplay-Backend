package com.deterior.global.exception

open class ImageException(
    override val message: String,
    override val value: String,
    override val errorCode: ErrorCode
) : DesplayApplicationException(message = message, value = value, errorCode = errorCode)

class ImageNotSupportedTypeException(
    override val message: String,
    override val value: String,
    override val errorCode: ErrorCode
) : ImageException(message = message, value = value, errorCode = errorCode)

class ImageExceedCapacityException(
    override val message: String,
    override val value: String,
    override val errorCode: ErrorCode
) : ImageException(message = message, value = value, errorCode = errorCode)