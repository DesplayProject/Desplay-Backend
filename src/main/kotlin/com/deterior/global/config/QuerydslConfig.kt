package com.deterior.global.config

import com.querydsl.jpa.JPQLTemplates
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class QuerydslConfig {
    @Bean
    fun jpaQueryFactory(em: EntityManager): JPAQueryFactory = JPAQueryFactory(JPQLTemplates.DEFAULT, em)
}