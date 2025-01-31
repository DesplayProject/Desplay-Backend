package com.deterior.domain.scrap.repository

import com.deterior.domain.board.QBoard
import com.deterior.domain.board.QBoard.*
import com.deterior.domain.member.QMember
import com.deterior.domain.member.QMember.*
import com.deterior.domain.scrap.QScrap
import com.deterior.domain.scrap.QScrap.*
import com.deterior.domain.scrap.Scrap
import com.deterior.domain.scrap.dto.ScrapHandleDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ScrapRepositoryImpl @Autowired constructor(
    private val jpaQueryFactory: JPAQueryFactory,
) : ScrapSearchRepository {
    override fun findScrapByUsernameAndBoardId(scrapHandleDto: ScrapHandleDto): Scrap {
        val queryResult = jpaQueryFactory
            .select(scrap)
            .from(scrap)
            .where(
                scrap.member.username.eq(scrapHandleDto.username).and(
                board.id.eq(scrapHandleDto.boardId)
                ))
            .fetch()
        return queryResult[0]
    }
}
//where username := username and id := id