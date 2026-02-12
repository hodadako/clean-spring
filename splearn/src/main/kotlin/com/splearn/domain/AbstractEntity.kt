package com.splearn.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.proxy.HibernateProxy

@MappedSuperclass
abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    final override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other == null) return false
        val otherEffectiveClass = resolvePersistenceClass(other)
        val thisEffectiveClass = resolvePersistenceClass(this)
        if (thisEffectiveClass != otherEffectiveClass) return false
        other as AbstractEntity

        return id != 0L && id == other.id
    }

    final override fun hashCode(): Int = resolvePersistenceClass(this).hashCode()

    private fun resolvePersistenceClass(obj: Any?): Class<*> =
        if (obj is HibernateProxy) (this as HibernateProxy).hibernateLazyInitializer.persistentClass else this.javaClass

    override fun toString(): String {
        return "AbstractEntity(id=$id)"
    }
}
