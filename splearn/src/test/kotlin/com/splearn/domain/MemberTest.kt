package com.splearn.domain

import com.splearn.domain.MemberFixture.Companion.createMemberRegisterRequest
import com.splearn.domain.MemberFixture.Companion.createPasswordEncoder
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberTest {
    private lateinit var member: Member
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        passwordEncoder = createPasswordEncoder()
        member = Member.register(
            memberRegisterRequest = createMemberRegisterRequest(),
            passwordEncoder = passwordEncoder
        )
    }

    @Test
    fun createMember() {
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun activate() {
        member.activate()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @Test
    fun activateFail() {
        member.activate()

        assertThatThrownBy { member.activate() }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun deactivate() {
        member.activate()

        member.deactivate()

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun deactivateFail() {
        assertThatThrownBy { member.deactivate() }.isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun verifyPassword() {
        assertThat(member.verifyPassword(member.passwordHash, passwordEncoder)).isTrue()
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse()
    }

    @Test
    fun changeNickName() {
        assertThat(member.nickname).isEqualTo("hodako")

        member.changeNickname("Test")

        assertThat(member.nickname).isEqualTo("Test")
    }

    @Test
    fun changePassword() {
        member.changePassword("verysecret", passwordEncoder)

        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue()
    }

    @Test
    fun isActive() {
        assertThat(member.isActive()).isFalse()

        member.activate()

        assertThat(member.isActive()).isTrue()

        member.deactivate()

        assertThat(member.isActive()).isFalse()
    }

    @Test
    fun invalidEmail() {
        assertThatThrownBy {
            Member.register(
                memberRegisterRequest = createMemberRegisterRequest("invalid email"),
                passwordEncoder = passwordEncoder
            )
        }.isInstanceOf(IllegalArgumentException::class.java)

        Member.register(
            memberRegisterRequest = createMemberRegisterRequest(),
            passwordEncoder = passwordEncoder
        )
    }
}
