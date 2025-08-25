package com.splearn.application.provided

import com.splearn.SplearnTestConfiguration
import com.splearn.domain.MemberFixture
import com.splearn.domain.MemberStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(SplearnTestConfiguration::class)
class MemberRegisterTest {
    @Autowired
    private lateinit var memberRegister: MemberRegister

    @Test
    fun register() {
        val member = memberRegister.register(MemberFixture.createMemberRegisterRequest())

        assertAll(
            { assertThat(member.id).isNotNull() },
            { assertThat(member.status).isEqualTo(MemberStatus.PENDING) }
        )
    }
}
