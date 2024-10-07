package com.deterior

import com.google.common.base.CaseFormat
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class DatabaseCleanup : InitializingBean {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    private var tableNames: List<String>? = null

    override fun afterPropertiesSet() {
        tableNames = entityManager!!.metamodel.entities.filter {
            it.javaType.getAnnotation(Entity::class.java) != null
        }
            .map { CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, it.name) }
            .toList()
    }

    @Transactional
    fun execute() {
        entityManager!!.flush()
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate()
        for(entity in tableNames!!) {
            createTruncateQuery(entity)
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate()
    }

    private fun createTruncateQuery(tableName: String) =
        entityManager!!.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
}