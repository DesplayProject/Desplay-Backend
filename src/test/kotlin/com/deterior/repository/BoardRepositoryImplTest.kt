package com.deterior.repository

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.Board
import com.deterior.domain.board.MoodType
import com.deterior.domain.board.QBoard
import com.deterior.domain.board.QBoard.*
import com.deterior.domain.board.dto.BoardFindDto
import com.deterior.domain.board.repository.BoardRepository
import com.deterior.domain.image.dto.ImageFindDto
import com.deterior.domain.item.QItem
import com.deterior.domain.item.QItem.*
import com.deterior.domain.item.dto.ItemFindDto
import com.deterior.domain.member.Member
import com.deterior.domain.member.QMember
import com.deterior.domain.member.QMember.*
import com.deterior.domain.member.repository.MemberRepository
import com.deterior.domain.scrap.QScrap
import com.deterior.domain.scrap.QScrap.*
import com.deterior.domain.scrap.Scrap
import com.deterior.domain.scrap.repository.ScrapRepository
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
    val jpaQueryFactory: JPAQueryFactory,
    val scrapRepository: ScrapRepository,
    val memberRepository: MemberRepository,
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
            ) }
        for (v in find) {
            val isContain = v.items
                .any { it.title.contains(keyword) }
            isContain shouldBe true
            //println(v)
        }
    }

    @Test
    @Transactional
    fun `좋아요한 게시물 조회`() {
        val members = fillMembers()
        val boards = fillBoards(members)
        fillScraps(members, boards)
        val userId: Long = 1
        val result = jpaQueryFactory
            .selectFrom(scrap)
            .where(scrap.member.id.eq(userId))
            .offset(0)
            .limit(50)
            .fetch()
        val find = result
            .map { it -> BoardFindDto(
                boardId = it.board.id!!,
                title = it.board.title,
                moodTypes = it.board.moodTypes,
                username = it.member.username,
                scrapCount = it.board.scrapCount,
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
        for (v in find) {
            v.username shouldBe "username0"
            //println(v)
        }
    }

    @Test
    @Transactional
    fun `내가 쓴 게시물 조회`() {
        val members = fillMembers()
        val boards = fillBoards(members)
        fillScraps(members, boards)
        val result = jpaQueryFactory
            .selectFrom(board)
            .where(board.member.username.eq("username1"))
            .offset(0)
            .limit(50)
            .fetch()
        val find = result
            .map { it -> BoardFindDto(
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
            ) }
        for (v in find) {
            v.username shouldBe "username1"
            println(v)
        }
    }

    private fun fillMembers(): MutableList<Member> {
        val members = mutableListOf<Member>()
        for (i in 0..1) {
            val member = Member(
                username = "username$i",
                password = "password$i",
                email = "email$i",
                roles = mutableListOf()
            )
            memberRepository.save(member)
            members.add(member)
        }
        return members
    }

    private fun fillBoards(members: List<Member>): MutableList<Board> {
        val boards = mutableListOf<Board>()
        val moodTypes = mutableListOf(MoodType.NEAT, MoodType.CALM, MoodType.OFFICE, MoodType.FANCY, MoodType.GAMING)
        for (i in 0..3) {
            val board = Board(
                title = "title$i",
                content = "content$i",
                moodTypes = mutableListOf(moodTypes[i % 5], moodTypes[(i + 1) % 5]),
                member = members[i / 2]
            )
            boardRepository.save(board)
            boards.add(board)
        }
        return boards
    }

    private fun fillScraps(members: MutableList<Member>, boards: MutableList<Board>): List<Scrap> {
        val scraps = mutableListOf<Scrap>()
        for (i in 0..3) {
            val scrap = Scrap(
                member = members[i / 2],
                board = boards[i],
            )
            scrapRepository.save(scrap)
            scraps.add(scrap)
        }
        return scraps
    }
}