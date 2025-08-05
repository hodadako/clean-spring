package com.splearn.domain

class Member(
    var name: String,
    var nickname: String,
    var passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING
) {
    fun activate() {
        check(status == MemberStatus.PENDING) {"PENDING 상태가 아닙니다"}

        status = MemberStatus.ACTIVE
    }
}