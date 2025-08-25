package com.splearn.application.provided

import com.splearn.application.MemberService
import com.splearn.application.required.EmailSender
import com.splearn.application.required.MemberRepository
import com.splearn.domain.Email
import com.splearn.domain.Member
import com.splearn.domain.MemberFixture.createMemberRegisterRequest
import com.splearn.domain.MemberFixture.createPasswordEncoder
import com.splearn.domain.MemberStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils

class MemberRegisterTest {
    @Test
    fun registerTestStub() {
        val register = MemberService(MemberRepositoryStub(), EmailSenderStub(), createPasswordEncoder())

        val member = register.register(createMemberRegisterRequest())

        assertAll(
            { assertThat(member.id).isNotNull() },
            { assertThat(member.status).isEqualTo(MemberStatus.PENDING) }
        )
    }

    @Test
    fun registerTestMock() {
        val emailSenderMock = EmailSenderMock()
        val register = MemberService(MemberRepositoryStub(), emailSenderMock, createPasswordEncoder())

        val member = register.register(createMemberRegisterRequest())

        assertAll(
            { assertThat(member.id).isNotNull() },
            { assertThat(member.status).isEqualTo(MemberStatus.PENDING) },
            { assertThat(emailSenderMock.toSend).hasSize(1) },
            { assertThat(emailSenderMock.toSend.get(0)).isEqualTo(member.email) }
        )
    }

    class MemberRepositoryStub : MemberRepository {
        override fun save(member: Member): Member {
            ReflectionTestUtils.setField(member, "id", 1L)
            return member
        }
    }

    class EmailSenderStub : EmailSender {
        override fun send(email: Email, body: String, subject: String) {
        }
    }

    class EmailSenderMock : EmailSender {
        val toSend = mutableListOf<Email>()

        override fun send(email: Email, body: String, subject: String) {
            toSend.add(email)
        }
    }
}
