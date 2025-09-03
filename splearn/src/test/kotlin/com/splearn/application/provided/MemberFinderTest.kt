package com.splearn.application.provided

import com.splearn.SplearnTestConfiguration
import com.splearn.domain.MemberFixture
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Import(SplearnTestConfiguration::class)
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberFinderTest(
    val memberFinder: MemberFinder,
    val memberRegister: MemberRegister,
    val entityManager: EntityManager
) {
    @Test
    fun find() {
        val member = memberRegister.register(MemberFixture.createMemberRegisterRequest())
        entityManager.flush()
        entityManager.clear()

        val found = memberFinder.find(memberId = member.id)

        assertThat(member.id).isEqualTo(found.id)
    }

    @Test
    fun findFail() {
        assertThatThrownBy {
            memberFinder.find(999L)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
