package com.splearn.domain

/**
 * DTO for {@link com.splearn.domain.Member}
 */
data class MemberCreateRequest(
    val email: String,
    val nickname: String,
    val password: String
)
