package com.splearn

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest()
@Import(SplearnTestConfiguration::class)
class SplearnApplicationTests {

    @Test
    fun contextLoads() {
    }
}
