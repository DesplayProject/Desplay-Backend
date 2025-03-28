package com.deterior.domain.member.repository

import com.deterior.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsername(username: String): Member?
    fun findByEmail(email: String): Optional<Member>
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}