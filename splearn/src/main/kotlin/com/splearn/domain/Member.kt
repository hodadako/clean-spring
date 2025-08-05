package com.splearn.domain

class Member(
    var name: String,
    var nickname: String,
    var passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING
)