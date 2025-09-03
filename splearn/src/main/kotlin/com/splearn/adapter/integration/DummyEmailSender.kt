package com.splearn.adapter.integration

import com.splearn.application.required.EmailSender
import com.splearn.domain.Email
import org.springframework.context.annotation.Fallback
import org.springframework.stereotype.Component

@Component
@Fallback
class DummyEmailSender : EmailSender {
    override fun send(email: Email, body: String, subject: String) {
        println("DummyEmailSender sending email $email")
    }
}
