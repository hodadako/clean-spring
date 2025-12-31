package com.splearn.adapter.security

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SecurePasswordEncoderTest {
    @Test
    fun securePasswordEncoder() {
        val securePasswordEncoder = SecurePasswordEncoder(BCryptPasswordEncoder())
        val password = "mySecretPassword"
        val passwordHash = securePasswordEncoder.encode(password)

        assertAll({
            assertThat(securePasswordEncoder.matches(password, passwordHash)).isTrue()
            assertThat(securePasswordEncoder.matches("wrongPassword", passwordHash)).isFalse()
        })
    }
}
