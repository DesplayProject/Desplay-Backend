package com.deterior.service

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
import kotlin.test.AfterTest

@SpringBootTest
class AutoCompleteServiceTest @Autowired constructor(
    val applicationProperties: ApplicationProperties,
    val redisTemplate: RedisTemplate<String, String>
) : AnnotationSpec() {

    val limit = applicationProperties.autoComplete.limit

    @AfterTest
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
        zSetOperations.add("autoComplete", "가", -100.0)
        zSetOperations.add("autoComplete", "가나*", 0.0)
        zSetOperations.add("autoComplete", "가다*", 0.0)
        var autoCompletes = findKeywords(keyword, zSetOperations)
        println(autoCompletes)
        autoCompletes[0] shouldBe "가나"
        autoCompletes[1] shouldBe "가다"

        zSetOperations.add("autoComplete", "가다*", -1.0)
        autoCompletes = findKeywords(keyword, zSetOperations)
        autoCompletes[0] shouldBe "가다"
        autoCompletes[1] shouldBe "가나"
    }

    private fun findKeywords(keyword: String, zSetOperations: ZSetOperations<String, String>): List<String> {
        var autoCompletes = emptyList<String>()
        zSetOperations.rank("autoComplete", keyword)?.let {
            val list = zSetOperations.range("autoComplete", it, it + 1000) as Set<String>
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