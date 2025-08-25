package com.splearn.application.provided

import com.splearn.SplearnTestConfiguration
import com.splearn.domain.MemberFixture
import com.splearn.domain.MemberStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@SpringBootTest
@Import(SplearnTestConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
data class MemberRegisterTest(
    val memberRegister: MemberRegister
) {
    @Test
    fun register() {
        val member = memberRegister.register(MemberFixture.createMemberRegisterRequest())

        assertAll(
            { assertThat(member.id).isNotNull() },
            { assertThat(member.status).isEqualTo(MemberStatus.PENDING) }
        )
    }

    @Test
    fun duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest("hodako2401@splearn.app"))

        assertThatThrownBy { memberRegister.register(MemberFixture.createMemberRegisterRequest()) }
            .isInstanceOf(
                DuplicateEmailException::class.java
            )
    }
}
