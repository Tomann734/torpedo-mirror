package de.torpedomirror.backend.persistence.base

import jakarta.persistence.Column
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime

abstract class BaseEntity {
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: ZonedDateTime? = null

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false, updatable = true)
    var updatedAt: ZonedDateTime? = null
}