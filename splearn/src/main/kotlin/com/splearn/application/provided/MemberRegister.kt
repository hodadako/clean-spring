package com.splearn.application.provided

import com.splearn.domain.Member
import com.splearn.domain.MemberRegisterRequest

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
interface MemberRegister {
    fun register(registerRequest: MemberRegisterRequest): Member

    fun activate(memberId: Long): Member
}
