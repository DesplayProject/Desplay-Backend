package com.deterior.repository

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.QBoard
import com.deterior.domain.board.QBoard.*
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.image.dto.ImageFindDto
import com.deterior.domain.item.QItem
import com.deterior.domain.item.QItem.*
import com.deterior.domain.item.dto.ItemFindDto
import com.deterior.domain.member.QMember
import com.deterior.domain.member.QMember.*
import com.querydsl.core.group.GroupBy
import com.querydsl.core.group.GroupBy.*
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import io.kotest.matchers.string.shouldContain
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.meta.When

@SpringBootTest
class BoardRepositoryImplTest @Autowired constructor(
    val boardRepository: BoardRepository,
    val databaseCleanup: DatabaseCleanup,
    val jpaQueryFactory: JPAQueryFactory
) : AnnotationSpec() {

    @AfterEach
    fun afterEach() {
        databaseCleanup.execute()
    }

    @Test
    @Transactional
    fun `조건에 따라 검색 후 DTO로 변환`() {
        val keyword = "item1"
        val result = jpaQueryFactory
            .selectFrom(board)
            .join(member).on(board.member.id.eq(member.id))
            .join(item).on(item.board.id.eq(board.id))
            .where(item.title.contains(keyword))
            .offset(0)
            .limit(50)
            .fetch()
        val find: List<BoardFindDto> = result
            .map { it -> BoardFindDto(
                boardId = it.id!!,
                title = it.title,
                moodTypes = it.moodTypes,
                username = it.member.username,
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
        for (v in find) {
            val isContain = v.items
                .any { it.title.contains(keyword) }
            isContain shouldBe true
            println(v)
        }
    }
}