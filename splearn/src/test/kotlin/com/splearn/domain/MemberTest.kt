package com.splearn.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MemberTest {
    @Test
    fun `Create Member`() {
        val member = Member("toby@splearn.app", "Toby", "secret")

        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }
}