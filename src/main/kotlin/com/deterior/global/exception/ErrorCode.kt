package com.deterior.global.exception

enum class ErrorCode(
    val code: String,
    val message: String
) {
    DUPLICATE_USERNAME("M-001", "Duplicate username"),
    DUPLICATE_EMAIL("M-002", "Duplicate email"),
    EMAIL_AUTH_FAIL("M-003", "Email authentication failed"),

    NO_SUCH_BOARD("B-001", "there is no such board"),
}