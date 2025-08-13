package com.splearn.application.required

import com.splearn.domain.Email

/**
 * 이메일을 발송한다
 */
interface EmailSender {
    fun send(email: Email, body: String, subject: String)
}
