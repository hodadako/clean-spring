package com.splearn.domain

class Member private constructor(
    var name: String,
    var nickname: String,
    var passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING
) {
    companion object {
        fun create(name: String, nickname: String, passwordHash: String, passwordEncoder: PasswordEncoder): Member {
            return Member(name, nickname, passwordEncoder.encode(passwordHash))
        }
    }

    fun activate() {
        check(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다" }

        status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        check(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다" }

        status = MemberStatus.DEACTIVATED
    }

    fun verifyPassword(secret: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(secret, passwordHash)
    }
}
