package com.deterior.global.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig {
    @Bean
    fun jpaQueryFactory(em: EntityManager): JPAQueryFactory = JPAQueryFactory(em)
}