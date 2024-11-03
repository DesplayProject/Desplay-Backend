package com.deterior.service

import com.deterior.global.service.AutoCompleteService
import com.deterior.global.util.ApplicationProperties
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import java.time.LocalDateTime
import kotlin.test.AfterTest

@SpringBootTest
class AutoCompleteServiceTest @Autowired constructor(
    val applicationProperties: ApplicationProperties,
    val redisTemplate: RedisTemplate<String, String>,
    val autoCompleteService: AutoCompleteService
) : AnnotationSpec() {

    val limit = applicationProperties.autoComplete.limit

    @AfterEach
    fun clean() {
        redisTemplate.delete("autoComplete")
    }

    @Test
    fun `자동완성`() {
        val keyword = "아이"
        val zSetOperations = redisTemplate.opsForZSet()
        addKeywords(zSetOperations)
        val autoCompletes = findKeywords(keyword, zSetOperations)
        autoCompletes[0] shouldBe "아이스크림"
    }

    @Test
    fun `단어에 가중치 업데이트`() {
        val keyword = "가"
        val zSetOperations = redisTemplate.opsForZSet()
        zSetOperations.add("autoComplete", "가", Int.MIN_VALUE.toDouble())
        zSetOperations.add("autoComplete", "가나*", 0.0)
        zSetOperations.add("autoComplete", "가다*", 0.0)
        var autoCompletes = findKeywords(keyword, zSetOperations)
        autoCompletes[0] shouldBe "가나"
        autoCompletes[1] shouldBe "가다"

        val score = zSetOperations.score("autoComplete", "가다*")
        zSetOperations.add("autoComplete", "가다*", (score!!-1))
        autoCompletes = findKeywords(keyword, zSetOperations)
        autoCompletes[0] shouldBe "가다"
        autoCompletes[1] shouldBe "가나"
    }

    @Test
    fun `자료 구조에 단어 저장`() {
        val input = "자연은맛있다튀기지않고바람으로말린생라면"
        val zSetOperations = redisTemplate.opsForZSet()
        saveZSet(input, zSetOperations)
        zSetOperations.score("autoComplete", "$input*") shouldBe 0.0
        saveZSet(input, zSetOperations)
        zSetOperations.score("autoComplete", "$input*") shouldBe -1.0
        saveZSet(input, zSetOperations)
        zSetOperations.score("autoComplete", "$input*") shouldBe -2.0
    }

    @Test
    fun `자동완성 서비스 테스트`() {
        autoCompleteService.updateAutoComplete("그래픽카드 4070")
        autoCompleteService.updateAutoComplete("그래픽카드 4070")
        autoCompleteService.updateAutoComplete("그래픽카드 4080")
        autoCompleteService.updateAutoComplete("그래픽카드 4080 super")
        autoCompleteService.updateAutoComplete("그래픽카드 4080 super")
        autoCompleteService.updateAutoComplete("그래픽카드 4080 super")
        val result = autoCompleteService.getAutoComplete("그래픽")
        result.list?.get(0) shouldBe "그래픽카드 4080 super"
        result.list?.get(1) shouldBe "그래픽카드 4070"
        result.list?.get(2) shouldBe "그래픽카드 4080"
    }

    private fun saveZSet(input: String, zSetOperations: ZSetOperations<String, String>) {
        var str = ""
        val score = zSetOperations.score("autoComplete", "$input*")
        if (score == null) {
            for (s in input) {
                str+=s
                if (str.length == input.length) str+="*"
                zSetOperations.add("autoComplete", str, 0.0)
            }
        } else {
            zSetOperations.add("autoComplete", "$input*", score-1)
        }

    }

    private fun findKeywords(keyword: String, zSetOperations: ZSetOperations<String, String>): List<String> {
        var autoCompletes = emptyList<String>()
        zSetOperations.rank("autoComplete", keyword)?.let {
            val list = zSetOperations.range("autoComplete", it , it + 1000) as Set<String>
            autoCompletes = list.stream()
                .filter { it -> it.startsWith(keyword) && it.endsWith("*") }
                .map { it -> it.removeSuffix("*") }
                .limit(limit)
                .toList()
        }
        return autoCompletes
    }

    private fun addKeywords(zSetOperations: ZSetOperations<String, String>) {
        zSetOperations.add("autoComplete", "아이", 0.0)
        zSetOperations.add("autoComplete", "아이스", 0.0)
        zSetOperations.add("autoComplete", "아이스크", 0.0)
        zSetOperations.add("autoComplete", "아이스크림*", 0.0)
    }
}