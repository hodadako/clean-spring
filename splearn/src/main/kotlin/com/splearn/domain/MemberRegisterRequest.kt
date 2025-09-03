package com.splearn.domain

data class MemberRegisterRequest(
    val email: String,
    val nickname: String,
    val password: String
) {
    init {
        FieldValidationUtils.email(email)
        FieldValidationUtils.size(nickname, "닉네임 길이가 올바르지 않습니다.", 5, 20)
        FieldValidationUtils.size(password, "비밀번호 길이가 올바르지 않습니다.", 8, 100)
    }
}
