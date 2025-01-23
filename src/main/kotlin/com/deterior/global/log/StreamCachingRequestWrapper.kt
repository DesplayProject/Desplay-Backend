package com.deterior.global.log

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.apache.tomcat.util.http.fileupload.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class StreamCachingRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private var contents = ByteArrayOutputStream()

    override fun getInputStream(): ServletInputStream {
        IOUtils.copy(super.getInputStream(), contents)

        return object : ServletInputStream() {
            private var buffer = ByteArrayInputStream(contents.toByteArray())
            override fun read(): Int = buffer.read()
            override fun isFinished(): Boolean = buffer.available() == 0
            override fun isReady(): Boolean = true
            override fun setReadListener(listener: ReadListener?) =
                throw java.lang.RuntimeException("Not implemented")
        }
    }

    fun getContents(): ByteArray = this.inputStream.readAllBytes()
}