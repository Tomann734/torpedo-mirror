package de.torpedomirror.backend.persistence.module.base

import de.torpedomirror.backend.dto.module.SubmoduleDto
import de.torpedomirror.backend.persistence.base.BaseEntity
import jakarta.persistence.*
import java.time.ZonedDateTime
import java.util.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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
