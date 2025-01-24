package com.deterior.domain.board.dto

import com.deterior.domain.board.repository.SearchType
import com.deterior.domain.board.repository.SortType
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "검색 조건 DTO")
data class BoardSearchCondition(

    val tags: List<String>?,

    val keyword: String?,

    val username: String?,

    val sortTypes: List<SortType>?,

    val searchType: SearchType
)