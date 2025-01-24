package com.deterior.domain.board.dto

import com.deterior.domain.member.dto.MemberContext
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(description = "게시글 저장 DTO")
data class BoardWriteRequest(

    @Schema(description = "제목", required = true)
    val title: String,

    @Schema(description = "내용", required = true)
    val content: String,

    @Schema(description = "태그들", required = true)
    val tags: List<String>,

    @Schema(description = "상품들 [pair 형식으로 작성 예){first:상품명, second:상품 링크}]", required = true)
    val items: List<Pair<String, String>>
) {
    fun toWriteDto(
        files: List<MultipartFile>,
        memberContext: MemberContext
    ): BoardWriteDto = BoardWriteDto(
        title = title,
        content = content,
        tags = tags,
        files = files,
        items = items,
        memberDto = memberContext.memberDto
    )
}