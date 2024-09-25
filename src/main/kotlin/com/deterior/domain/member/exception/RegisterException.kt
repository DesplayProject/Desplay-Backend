package com.deterior.domain.member.exception

open class RegisterException(
    override val message: String,
    open val value: String
) : RuntimeException()

class DuplicateUsernameException(
    override val value: String,
    override val message: String
) : RegisterException(message = message, value = value)

class DuplicateEmailException(
    override val value: String,
    override val message: String
) : RegisterException(message = message, value = value)