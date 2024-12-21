package com.deterior.domain.follow

import com.deterior.domain.BaseEntity
import com.deterior.domain.follow.dto.FollowDto
import com.deterior.domain.member.Member
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class Follow (
    @ManyToOne
    val from: Member,

    @ManyToOne
    val to: Member,
) : BaseEntity() {
    fun toDto() = FollowDto(
        followId = id!!,
        from = from.toDto(),
        to = to.toDto()
    )
}