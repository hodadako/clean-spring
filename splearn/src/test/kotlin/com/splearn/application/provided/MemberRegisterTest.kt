package com.splearn.application.provided

import com.splearn.SplearnTestConfiguration
import com.splearn.domain.MemberFixture
import com.splearn.domain.MemberRegisterRequest
import com.splearn.domain.MemberStatus
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

@SpringBootTest
@Import(SplearnTestConfiguration::class)
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
data class MemberRegisterTest(
    val memberRegister: MemberRegister,
    val entityManager: EntityManager
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

        assertThatThrownBy { memberRegister.register(MemberFixture.createMemberRegisterRequest("hodako2401@splearn.app")) }
            .isInstanceOf(
                DuplicateEmailException::class.java
            )
    }

    @Test
    fun activate() {
        val member = memberRegister.register(MemberFixture.createMemberRegisterRequest())

        entityManager.flush()
        entityManager.clear()

        memberRegister.activate(member.id)

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidMemberRegisterRequestProvider")
    fun memberRegisterRequestFail(sut: () -> MemberRegisterRequest) {
        assertThatThrownBy { memberRegister.register(sut()) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    companion object {
        @JvmStatic
        fun invalidMemberRegisterRequestProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    named(
                        "이메일이 올바르지 않을 때"
                    ) {
                        MemberRegisterRequest(
                            email = "hodako.splearn.app",
                            nickname = "hodako",
                            password = "password"
                        )
                    }
                ),
                Arguments.of(
                    named(
                        "닉네임 길이가 올바르지 않을 때"
                    ) {
                        MemberRegisterRequest(
                            email = "hodako51@splearn.app",
                            nickname = "hoda",
                            password = "password"
                        )
                    }
                ),
                Arguments.of(
                    named(
                        "비밀번호 길이가 올바르지 않을 때"
                    ) {
                        MemberRegisterRequest(
                            email = "hodako15@splearn.app",
                            nickname = "hodako",
                            password = "passwo"
                        )
                    }
                )
            )
        }
    }
}
