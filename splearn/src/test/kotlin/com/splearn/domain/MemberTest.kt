package com.splearn.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberTest {
    private lateinit var member: Member
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        passwordEncoder = object : PasswordEncoder {
            override fun encode(password: String): String {
                return password.uppercase()
            }

            override fun matches(password: String, passwordHash: String): Boolean {
                return encode(password) == passwordHash
            }
        }
        member = Member.create(
            name = "toby@splearn.app",
            nickname = "Toby",
            passwordHash = "secret",
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
        assertThat(member.nickname).isEqualTo("Toby")

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
}
