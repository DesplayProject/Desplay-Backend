package com.deterior.util

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LogConvertTest : FunSpec({
    test(", 마다 건너뛰기") {
        val log = """ uuid":"c28d5890-17d4-41ff-8210-53bb41b71080","method":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzQwNDk2MjYxfQ.dDLY9Ck_6318dXYKeSy8TE0WFBODzUfnDk25AkDggMgddddddddddddddddddddddddddddddddddddddddd","url":"/api/member/sign-in","clientIp":"0:0:0:0:0:0:0:1","params": okay"""
        val result = log.split(",")
            .map { if(it.length > 150) it.substring(0, 150) + "..." else it }
            .joinToString(",\n")
        println(result)
    }
})