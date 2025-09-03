package com.splearn.application.provided

import com.splearn.domain.Member

interface MemberFinder {
    fun find(memberId: Long): Member
}
