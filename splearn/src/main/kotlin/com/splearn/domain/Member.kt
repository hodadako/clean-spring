package com.splearn.domain

class Member private constructor(
    var email: Email,
    var nickname: String,
    var passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING
) {
    companion object {
        fun refactor(memberCreateRequest: MemberCreateRequest, passwordEncoder: PasswordEncoder): Member {
            return Member(
                email = Email(memberCreateRequest.email),
                nickname = memberCreateRequest.nickname,
                passwordHash = passwordEncoder.encode(memberCreateRequest.password)
            )
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

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        passwordHash = passwordEncoder.encode(password)
    }

    fun isActive(): Boolean {
        return status == MemberStatus.ACTIVE
    }
}
