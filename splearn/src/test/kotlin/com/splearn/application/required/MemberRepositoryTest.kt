package com.splearn.application.required

import com.splearn.domain.Member
import com.splearn.domain.MemberFixture.createMemberRegisterRequest
import com.splearn.domain.MemberFixture.createPasswordEncoder
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun createMember() {
        val member = Member.register(createMemberRegisterRequest(), createPasswordEncoder())

        memberRepository.save(member)

        assertThat(member.id).isNotZero()

        entityManager.flush()
    }

    @Test
    fun createDuplicateEmail() {
        val member1 = Member.register(createMemberRegisterRequest(), createPasswordEncoder())
        memberRepository.save(member1)

        val member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder())
        assertThatThrownBy { memberRepository.save(member2) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }
}
