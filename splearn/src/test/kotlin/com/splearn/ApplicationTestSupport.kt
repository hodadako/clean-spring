package com.splearn

import com.splearn.application.required.EmailSender
import com.splearn.domain.Email
import com.splearn.domain.MemberFixture.createPasswordEncoder
import com.splearn.domain.PasswordEncoder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class ApplicationTestSupport {
    @Bean
    fun emailSender(): EmailSender {
        return object : EmailSender {
            override fun send(email: Email, body: String, subject: String) {
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return createPasswordEncoder()
    }
}
