package com.deterior.repository

import com.deterior.global.exception.EmailAuthenticationFailException
import com.deterior.global.repository.MailCheckDao
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MailCheckDaoTest @Autowired constructor(
    val mailCheckDao: MailCheckDao
) : BehaviorSpec({

    Given("다양한 사용자의 인증 메일이 쌓인다") {
        val aNum = mailCheckDao.saveAuthNumber("a")
        val bNum = mailCheckDao.saveAuthNumber("b")
        val cNum = mailCheckDao.saveAuthNumber("c")
        When("사용자가 번호로 인증한다") {
            Then("올바른 번호로 인증이 성공한다") {
                mailCheckDao.authNumberCheck("a", aNum) shouldBe true
                mailCheckDao.authNumberCheck("b", bNum) shouldBe true
                mailCheckDao.authNumberCheck("c", cNum) shouldBe true
            }
            Then("올바르지 못한 번호로 인증이 실패한다") {
                shouldThrow<EmailAuthenticationFailException> { mailCheckDao.authNumberCheck("c", aNum) }
                shouldThrow<EmailAuthenticationFailException> { mailCheckDao.authNumberCheck("a", bNum) }
                shouldThrow<EmailAuthenticationFailException> { mailCheckDao.authNumberCheck("b", cNum) }
            }
        }

        When("같은 사용자가 여러번 인증번호를 요청한다") {
            mailCheckDao.saveAuthNumber("a")
            mailCheckDao.saveAuthNumber("a")
            val result = mailCheckDao.saveAuthNumber("a")
            Then("인증이 성공한다") {
                mailCheckDao.authNumberCheck("a", result) shouldBe true
            }
        }
    }
})