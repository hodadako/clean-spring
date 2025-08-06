package com.splearn.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmailTest {
    @Test
    fun equals() {
        val email1 = Email("hodako2401@gmail.com")
        val email2 = Email("hodako2401@gmail.com")

        assertThat(email1).isEqualTo(email2)
    }
}