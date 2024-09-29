package com.deterior.global.repository

import com.deterior.global.dto.MailCheckResponse
import com.deterior.global.dto.MailDto
import com.deterior.global.exception.EmailAuthenticationFailException
import com.deterior.global.exception.dto.ErrorCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import java.security.SecureRandom
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.abs

@Repository
class MailCheckDao {
    private val authNumbers = ConcurrentHashMap<String, Int>()

    fun saveAuthNumber(mail: String): Int {
        val authNum = createNumber()
        authNumbers[mail] = authNum
        return authNum
    }

    fun authNumberCheck(mail: String, authNumber: Int): Boolean {
        if(authNumbers.containsKey(mail)) {
            val result = authNumbers[mail]
            if(result == authNumber) {
                authNumbers.remove(mail)
                return true
            }
        }
        throw EmailAuthenticationFailException(
            "이메일 인증에 실패했습니다",
            authNumber.toString(),
            ErrorCode.EMAIL_AUTH_FAIL
        )
    }

    private fun createNumber() = abs(SecureRandom.getInstanceStrong().nextInt())
}