package com.deterior.domain.image.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "이미지 API")
interface ImageControllerSwagger {

    @Operation(summary = "이미지 받아오기", description = "이미지 ID로 받아옴")
    fun showImage(@Parameter(description = "이미지 ID", required = true) imageId: Long): ResponseEntity<ByteArray>
}