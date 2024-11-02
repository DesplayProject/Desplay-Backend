package com.deterior.domain.board.repository

import com.deterior.domain.board.MoodType
import com.deterior.domain.board.QBoard.*
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.repository.SortType.*
import com.deterior.domain.image.dto.ImageFindDto
import com.deterior.domain.item.QItem.*
import com.deterior.domain.item.dto.ItemFindDto
import com.deterior.domain.member.QMember
import com.deterior.domain.member.QMember.member
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils.*

@Repository
class BoardRepositoryImpl @Autowired constructor(
    private val jpaQueryFactory: JPAQueryFactory
) : BoardSearchRepository {
    override fun boardSearch(condition: BoardSearchCondition?, pageable: Pageable): Page<BoardFindDto> {
        val order = getOrder(condition)
        val queryResult = jpaQueryFactory
            .selectFrom(board)
            .join(member).on(board.member.id.eq(member.id))
            .join(item).on(item.board.id.eq(board.id))
            .where(
                containTitle(condition!!.keyword)?.or(
                    containItem(condition.keyword)
                ),
                containMoodType(condition.moodTypes),
                containMember(condition.username)
            )
            .groupBy(board.id)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*order.toTypedArray())
            .fetch()
        val content = queryResult
            .map { it ->
                BoardFindDto(
                    boardId = it.id!!,
                    title = it.title,
                    moodTypes = it.moodTypes,
                    username = it.member.username,
                    scrapCount = it.scrapCount,
                    items = it.items.map { it -> ItemFindDto(
                        itemId = it.id!!,
                        title = it.title,
                        link = it.link,
                    ) },
                    images = it.images.map { it -> ImageFindDto(
                        imageId = it.id!!,
                        saveFileName = it.saveFileName,
                    ) }
                )
            }
        return PageImpl(content, pageable, content.size.toLong())
    }

    private fun getOrder(condition: BoardSearchCondition?): List<OrderSpecifier<*>> {
        val result = mutableListOf<OrderSpecifier<*>>()
        if (condition!!.sortTypes == null) return emptyList()
        for (sort in condition.sortTypes!!) {
            when (sort) {
                DATE_ASC -> result.add(OrderSpecifier(Order.ASC, board.createTime))
                DATE_DESC -> result.add(OrderSpecifier(Order.DESC, board.createTime))
                LIKE_ASC -> result.add(OrderSpecifier(Order.ASC, board.scrapCount))
                LIKE_DESC -> result.add(OrderSpecifier(Order.DESC, board.scrapCount))
            }
        }
        return result
    }

    private fun containMember(username: String?): BooleanExpression? = if (hasText(username)) member.username.eq(username) else null

    private fun containTitle(title: String?): BooleanExpression? = if (hasText(title)) board.title.contains(title) else null

    private fun containItem(word: String?): BooleanExpression? = if (hasText(word)) item.title.contains(word) else null

    private fun containMoodType(moodTypes: List<MoodType>?): BooleanExpression? = if(!moodTypes.isNullOrEmpty()) board.moodTypes.any().`in`(moodTypes) else null
}