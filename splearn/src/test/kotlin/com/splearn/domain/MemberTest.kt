package com.splearn.domain

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberTest {
    private lateinit var member: Member

    @BeforeEach
    fun setUp() {
        val passwordEncoder = mockk<PasswordEncoder>()
        every { passwordEncoder.encode(any()) } returns "hashedPassword"
        member = Member.create("toby@splearn.app", "Toby", "secret", passwordEncoder)
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
}
