package com.deterior.global.log

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import java.io.ByteArrayInputStream
import java.io.InputStream

class CachedBodyServletInputStream(cachedBody: ByteArray) : ServletInputStream() {

    private val cachedBodyInputStream: InputStream = ByteArrayInputStream(cachedBody)

    override fun read() = cachedBodyInputStream.read()

    override fun isFinished() = cachedBodyInputStream.available() == 0

    override fun isReady() = true

    override fun setReadListener(p0: ReadListener?) {
        TODO("Not yet implemented")
    }
}