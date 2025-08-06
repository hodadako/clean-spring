package com.splearn.domain

@JvmInline
value class Email(
    val address: String,
) {
    init {
        val emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$".toRegex()
        require(address.matches(emailPattern)) { "이메일 형식이 올바르지 않습니다. $address" }
    }
}
