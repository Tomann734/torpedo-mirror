package de.torpedomirror.backend.persistence.base

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import org.springframework.data.domain.Persistable

@MappedSuperclass
abstract class ManuallyAssignedIdEntity<ID>: BaseEntity(), Persistable<ID> {
    @Transient
    private var isNew = true

    override fun isNew(): Boolean {
        return this.isNew
    }

    @PrePersist
    @PostLoad
    fun markNotNew() {
        this.isNew = false
    }
}