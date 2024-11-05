package com.deterior.domain.board.dto

import com.deterior.domain.board.repository.SearchType
import com.deterior.domain.board.repository.SortType

data class BoardSearchCondition(
    val tags: List<String>?,
    val keyword: String?,
    val username: String?,
    val sortTypes: List<SortType>?,
    val searchType: SearchType
)