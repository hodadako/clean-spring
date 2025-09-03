package com.splearn.domain

@JvmInline
value class Email(
    val address: String
) {
    init {
        FieldValidationUtils.email(address)
    }
}
