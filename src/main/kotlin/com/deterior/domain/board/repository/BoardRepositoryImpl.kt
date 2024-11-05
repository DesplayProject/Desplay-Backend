package com.deterior.domain.board.repository

import com.deterior.domain.board.Board
import com.deterior.domain.board.QBoard.*
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.dto.BoardSearchCondition
import com.deterior.domain.board.repository.SearchType.*
import com.deterior.domain.board.repository.SortType.*
import com.deterior.domain.image.dto.ImageFindDto
import com.deterior.domain.item.QItem.*
import com.deterior.domain.item.dto.ItemFindDto
import com.deterior.domain.member.QMember
import com.deterior.domain.member.QMember.member
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.member.service.MemberService
import com.deterior.domain.scrap.QScrap.scrap
import com.deterior.domain.scrap.Scrap
import com.deterior.domain.tag.QBoardTag
import com.deterior.domain.tag.QBoardTag.*
import com.deterior.domain.tag.QTag
import com.deterior.domain.tag.QTag.*
import com.deterior.domain.tag.dto.TagFindDto
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils.*

@Repository
class BoardRepositoryImpl @Autowired constructor(
    private val jpaQueryFactory: JPAQueryFactory,
    private val memberRepository: MemberRepository
) : BoardSearchRepository {
    override fun selectSearch(condition: BoardSearchCondition, pageable: Pageable): Page<BoardFindDto> {
        when (condition.searchType) {
            MAIN -> {
                val content = mainSearch(condition, pageable)
                return PageImpl(content, pageable, content.size.toLong())
            }
            MY_LIKE -> {
                val content = myLikeSearch(condition, pageable)
                return PageImpl(content, pageable, content.size.toLong())
            }
            MY_WRITE -> {
                val content = myWriteSearch(condition, pageable)
                return PageImpl(content, pageable, content.size.toLong())
            }
        }
    }

    fun mainSearch(condition: BoardSearchCondition, pageable: Pageable): List<BoardFindDto> {
        val order = getOrder(condition)
        val queryResult = jpaQueryFactory
            .selectFrom(board)
            .join(member).on(board.member.id.eq(member.id))
            .join(item).on(item.board.id.eq(board.id))
            .where(
                containTitle(condition.keyword)?.or(
                    containItem(condition.keyword)
                ),
                board.id.`in`(
                    JPAExpressions
                        .select(boardTag.board.id)
                        .from(boardTag)
                        .where(boardTag.tag.title.`in`(condition.tags))
                        .groupBy(boardTag.board.id)
                )
            )
            .groupBy(board.id)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*order.toTypedArray())
            .fetch()
        return boardToFindDto(queryResult)
    }

    fun myLikeSearch(condition: BoardSearchCondition, pageable: Pageable): List<BoardFindDto> {
        val order = getOrder(condition)
        val queryResult = jpaQueryFactory
            .selectFrom(scrap)
            .where(scrap.member.username.eq(condition.username))
            .offset(0)
            .limit(50)
            .orderBy(*order.toTypedArray())
            .fetch()
        return scrapToFindDto(queryResult)
    }

    fun myWriteSearch(condition: BoardSearchCondition, pageable: Pageable): List<BoardFindDto> {
        val order = getOrder(condition)
        val queryResult = jpaQueryFactory
            .selectFrom(board)
            .where(board.member.username.eq(condition.username))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*order.toTypedArray())
            .fetch()
        return boardToFindDto(queryResult)
    }

    private fun scrapToFindDto(scraps: List<Scrap>): List<BoardFindDto> = scraps
        .map { it -> BoardFindDto(
            boardId = it.board.id!!,
            title = it.board.title,
            username = it.member.username,
            scrapCount = it.board.scrapCount,
            tags = it.board.tags.map { it -> TagFindDto(
                tagId = it.tag.id!!,
                title = it.tag.title
            ) },
            items = it.board.items.map { it -> ItemFindDto(
                itemId = it.id!!,
                title = it.title,
                link = it.link,
            ) },
            images = it.board.images.map { it -> ImageFindDto(
                imageId = it.id!!,
                saveFileName = it.saveFileName,
            ) }
        ) }

    private fun boardToFindDto(boards: List<Board>): List<BoardFindDto> = boards
            .map { it ->
                BoardFindDto(
                    boardId = it.id!!,
                    title = it.title,
                    username = it.member.username,
                    scrapCount = it.scrapCount,
                    tags = it.tags.map { it -> TagFindDto(
                        tagId = it.tag.id!!,
                        title = it.tag.title
                    ) },
                    items = it.items.map { it -> ItemFindDto(
                        itemId = it.id!!,
                        title = it.title,
                        link = it.link,
                    ) },
                    images = it.images.map { it -> ImageFindDto(
                        imageId = it.id!!,
                        saveFileName = it.saveFileName,
                    ) }
                ) }

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

    private fun containTitle(title: String?): BooleanExpression? = if (hasText(title)) board.title.contains(title) else null

    private fun containItem(word: String?): BooleanExpression? = if (hasText(word)) item.title.contains(word) else null
}