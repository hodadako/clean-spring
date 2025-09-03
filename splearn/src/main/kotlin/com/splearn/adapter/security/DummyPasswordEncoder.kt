package com.splearn.adapter.security

import com.splearn.domain.PasswordEncoder
import org.springframework.context.annotation.Fallback
import org.springframework.stereotype.Component

@Component
@Fallback
class DummyPasswordEncoder : PasswordEncoder {
    override fun encode(password: String): String {
        return password.uppercase()
    }

    override fun matches(password: String, passwordHash: String): Boolean {
        return encode(password) == passwordHash
    }
}
