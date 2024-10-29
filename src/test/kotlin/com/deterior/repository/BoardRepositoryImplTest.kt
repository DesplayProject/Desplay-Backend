package com.deterior.repository

import com.deterior.DatabaseCleanup
import com.deterior.domain.board.repository.BoardRepository
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.meta.When

@SpringBootTest
class BoardRepositoryImplTest @Autowired constructor(
    val boardRepository: BoardRepository,
    val databaseCleanup: DatabaseCleanup
) : BehaviorSpec({

    afterSpec {
        databaseCleanup.execute()
    }

    Given("Board 검색용 repository가 존재한다") {
        When("Board를 검색한다") {
            Then("검색이 성공한다") {

            }
        }
    }
})