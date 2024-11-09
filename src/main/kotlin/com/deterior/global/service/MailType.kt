package com.deterior.global.service

enum class MailType(
    val title: String,
    val content: String
) {
    EMAIL_AUTH(
        "DesPlay 이메일 인증입니다",
        "<h3>인증번호를 입력하여 인증을 완료해 주세요</h3>" +
        "<h3>인증번호: </h3>"
    )
}