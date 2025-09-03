package com.splearn.domain

/*
* 이후에 개발할 때 참고 : require() 대신에 custom exception으로 handler에서 예외처리하는게 편할 듯
 */
object FieldValidationUtils {
    val EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$".toRegex()

    fun email(address: String) {
        require(address.matches(EMAIL_PATTERN)) { "이메일 형식이 올바르지 않습니다. $address" }
    }

    fun size(target: String, exceptionMessage: String, min: Int, max: Int) {
        require(target.length >= min && target.length <= max) { exceptionMessage }
    }
}
