package com.deterior.domain.board.repository

import com.deterior.domain.board.MoodType
import com.deterior.domain.board.QBoard
import com.deterior.domain.board.QBoard.*
import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.dto.BoardSearchDto
import com.deterior.domain.board.dto.QBoardSearchDto
import com.deterior.domain.image.QImage
import com.deterior.domain.image.QImage.*
import com.deterior.domain.item.QItem
import com.deterior.domain.item.QItem.*
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Wildcard
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import org.springframework.util.StringUtils.*

@Repository
class BoardRepositoryImpl @Autowired constructor(
    private val jpaQueryFactory: JPAQueryFactory
) : BoardSearchRepository {
    override fun search(condition: BoardSearchCondition?, pageable: Pageable): Page<BoardSearchDto> {
        val content = jpaQueryFactory
            .select(QBoardSearchDto(
                board.id.`as`("boardId")
            ))
            .from(board)
            .join(item).on(board.id.eq(item.board.id))
            .where(
                containTitle(condition!!.keyword)?.or(
                    containItem(condition.keyword)
                ),
                containMoodType(condition.moodTypes),
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
        val count = jpaQueryFactory
            .select(Wildcard.count)
            .from(board)
            .join(item).on(board.id.eq(item.board.id))
            .where(
                containTitle(condition.keyword)?.or(
                    containItem(condition.keyword)
                ),
                containMoodType(condition.moodTypes)
            )
            .fetchOne()
        println(count)
        return PageImpl(content, pageable, count!!)
    }

    private fun containTitle(title: String?): BooleanExpression? = if (hasText(title)) board.title.contains(title) else null

    private fun containItem(word: String?): BooleanExpression? = if (hasText(word)) item.title.contains(word) else null

    private fun containMoodType(moodTypes: List<MoodType>?): BooleanExpression? = if(!moodTypes.isNullOrEmpty()) board.moodTypes.any().`in`(moodTypes) else null
}