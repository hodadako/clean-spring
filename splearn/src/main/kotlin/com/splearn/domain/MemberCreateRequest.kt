package com.splearn.domain

/**
 * DTO for {@link com.splearn.domain.Member}
 */
data class MemberCreateRequest(
    val name: String,
    val nickname: String,
    val password: String
)