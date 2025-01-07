package com.deterior.domain.follow.repository

import com.deterior.domain.follow.Follow
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository : JpaRepository<Follow, Long> {
    fun findByFromIdAndToId(fromId: Long, toId: Long): Follow?
    fun findByFromId(fromId: Long): List<Follow>
    fun findByToId(toId: Long): List<Follow>
}