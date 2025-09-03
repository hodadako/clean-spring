package com.splearn.application

import com.splearn.application.provided.MemberFinder
import com.splearn.application.required.MemberRepository
import com.splearn.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberQueryService(
    private val memberRepository: MemberRepository
) : MemberFinder {
    override fun find(memberId: Long): Member {
        val found =
            memberRepository.findById(memberId) ?: throw IllegalArgumentException("회원을 찾을 수 없습니다. id : $memberId")
        return found
    }
}
