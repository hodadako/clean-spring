package com.splearn.application

import com.splearn.application.provided.MemberRegister
import com.splearn.application.required.EmailSender
import com.splearn.application.required.MemberRepository
import com.splearn.domain.Member
import com.splearn.domain.MemberRegisterRequest
import com.splearn.domain.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder
) : MemberRegister {
    override fun register(registerRequest: MemberRegisterRequest): Member {
        // check

        val member = Member.register(registerRequest, passwordEncoder)

        memberRepository.save(member)

        emailSender.send(member.email, "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.")

        return member
    }
}
