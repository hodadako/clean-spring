package com.splearn.domain

object MemberFixture {
    fun createMemberRegisterRequest(email: String): MemberRegisterRequest {
        return MemberRegisterRequest(
            email = email,
            nickname = "hodako",
            password = "secret_too_legit"
        )
    }

    fun createMemberRegisterRequest(): MemberRegisterRequest {
        return createMemberRegisterRequest("hodako@splearn.app")
    }

    fun createPasswordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(password: String): String {
                return password.uppercase()
            }

            override fun matches(password: String, passwordHash: String): Boolean {
                return encode(password) == passwordHash
            }
        }
    }
}
