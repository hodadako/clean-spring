package com.splearn.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache

@Entity
@Table(
    name = "member",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_member_email",
            columnNames = ["email"]
        )
    ]
)
@NaturalIdCache
class Member private constructor(
    @NaturalId
    @Column(length = 150, nullable = false)
    var email: Email,

    @Column(length = 100, nullable = false)
    var nickname: String,

    @Column(length = 200, nullable = false)
    var passwordHash: String,

    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.PENDING
) : AbstractEntity() {
    companion object {
        fun register(memberRegisterRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder): Member {
            return Member(
                email = Email(memberRegisterRequest.email),
                nickname = memberRegisterRequest.nickname,
                passwordHash = passwordEncoder.encode(memberRegisterRequest.password)
            )
        }
    }

    override fun toString(): String {
        return "Member(id=$id, email=$email, nickname=$nickname, status=$status)"
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
