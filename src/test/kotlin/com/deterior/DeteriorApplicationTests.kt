package com.deterior

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@TestPropertySource(locations = ["classpath:application.yml"])
@SpringBootTest
class DeteriorApplicationTests {

    @Test
    fun contextLoads() {
    }

}
