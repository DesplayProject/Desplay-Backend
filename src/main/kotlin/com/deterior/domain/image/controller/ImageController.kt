package com.deterior.domain.image.controller

import com.deterior.domain.image.dto.ImageShowRequest
import com.deterior.global.util.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.io.File
import java.nio.file.Files

@Controller
@RequestMapping("/api/image")
class ImageController @Autowired constructor(
    private val applicationProperties: ApplicationProperties,
) {
    @GetMapping("/show")
    fun showImage(@RequestBody imageShowRequest: ImageShowRequest): ResponseEntity<ByteArray> {
        val filename = imageShowRequest.filename
        val path = "${applicationProperties.upload.path}${filename}"
        val image = File(path)
        val header = HttpHeaders()
        header.add("Content-Type", Files.probeContentType(image.toPath()))
        return ResponseEntity(FileCopyUtils.copyToByteArray(image), header, HttpStatus.OK)
    }
}