package de.torpedomirror.backend.persistence.module.base

import de.torpedomirror.backend.dto.module.SubmoduleDto
import de.torpedomirror.backend.persistence.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, length = 50)
abstract class Submodule(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_name", nullable = false)
    val module: Module,

    @Column(name = "record_time", nullable = false, updatable = false)
    val recordTime: ZonedDateTime,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID? = null

    abstract fun toDto(): SubmoduleDto
}
