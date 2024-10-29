package com.deterior.global.util

import com.deterior.domain.board.Board
import com.deterior.domain.board.MoodType
import com.deterior.domain.image.Image
import com.deterior.domain.item.Item
import com.deterior.domain.member.Member
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class InitBoardService @Autowired constructor(
    private val entityManager: EntityManager
) {
    @Transactional
    fun init() {
        val member = Member(
            username = "username",
            password = "password",
            email = "email",
            roles = mutableListOf()
        )
        entityManager.persist(member)
        val boards = fillBoards(member)
        fillItems(boards)
        fillImages(boards)
    }

    private fun fillBoards(member: Member): MutableList<Board> {
        val boards = mutableListOf<Board>()
        val moodTypes = mutableListOf(MoodType.NEAT, MoodType.CALM, MoodType.OFFICE, MoodType.FANCY, MoodType.GAMING)
        for (i in 0..49) {
            val board = Board(
                title = "title$i",
                content = "content$i",
                moodTypes = mutableListOf(moodTypes[i % 5], moodTypes[(i + 1) % 5]),
                member = member
            )
            entityManager.persist(board)
            boards.add(board)
        }
        return boards
    }

    private fun fillItems(boards: MutableList<Board>): MutableList<Item> {
        val items = mutableListOf<Item>()
        for (i in 0..99) {
            val item = Item(
                title = "item$i",
                link = "link$i",
                board = boards[i / 2]
            )
            entityManager.persist(item)
            items.add(item)
        }
        return items
    }

    private fun fillImages(boards: MutableList<Board>): MutableList<Image> {
        val images = mutableListOf<Image>()
        for (i in 0..99) {
            val image = Image(
                originFileName = "origin$i",
                saveFileName = "save$i.jpg",
                board = boards[i / 2]
            )
            entityManager.persist(image)
            images.add(image)
        }
        return images
    }
}