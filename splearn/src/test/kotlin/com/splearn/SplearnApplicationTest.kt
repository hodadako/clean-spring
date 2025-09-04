package com.splearn

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

class SplearnApplicationTest {
    @Test
    fun `main should call SpringApplication run`() {
        mockkStatic(SpringApplication::class)
        val args = arrayOf<String>()
        every {
            SpringApplication.run(SplearnApplication::class.java, *args)
        } returns mockk<ConfigurableApplicationContext>()

        main(args)

        verify { SpringApplication.run(SplearnApplication::class.java, *args) }
    }
}
