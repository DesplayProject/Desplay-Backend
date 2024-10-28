package com.deterior.global.exception

enum class ErrorCode(
    val code: String,
    val message: String
) {
    DUPLICATE_USERNAME("M-001", "Duplicate username"),
    DUPLICATE_EMAIL("M-002", "Duplicate email"),
    EMAIL_AUTH_FAIL("M-003", "Email authentication failed"),

    NO_TITLE_IN_BOARD("B-001", "there is no title in board"),
    NO_CONTENT_IN_BOARD("B-002", "there is no content"),

    INCONSISTENT_TOKEN("T-001", "inconsistent token"),
    INVALID_TOKEN("T-002", "invalid token"),

    NOT_SUPPORTED_TYPE("I-001", "only support jpg or png"),
    EXCEEDED_CAPACITY("I-002", "capacity exceeded"),
}