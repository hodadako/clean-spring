package com.splearn.application

import com.splearn.application.provided.DuplicateEmailException
import com.splearn.application.provided.MemberRegister
import com.splearn.application.required.EmailSender
import com.splearn.application.required.MemberRepository
import com.splearn.domain.Member
import com.splearn.domain.MemberRegisterRequest
import com.splearn.domain.PasswordEncoder
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder
) : MemberRegister {
    override fun register(registerRequest: MemberRegisterRequest): Member {
        checkDuplicateEmail(registerRequest)

        val member = Member.register(registerRequest, passwordEncoder)

        memberRepository.save(member)

        sendWelcomeEmail(member)

        return member
    }

    override fun activate(memberId: Long): Member {
        val member =
            memberRepository.findById(memberId) ?: throw IllegalArgumentException("회원을 찾을 수 없습니다. id: $memberId")

        member.activate()

        return member
    }

    private fun sendWelcomeEmail(member: Member) {
        emailSender.send(member.email, "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.")
    }

    private fun checkDuplicateEmail(registerRequest: MemberRegisterRequest) {
        memberRepository.findByEmail(registerRequest.email)?.let {
            throw DuplicateEmailException("이미 존재하는 이메일입니다.")
        }
    }
}
