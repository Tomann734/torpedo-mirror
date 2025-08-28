package de.torpedomirror.backend.persistence.module.base

import de.torpedomirror.backend.dto.CreateModuleDto
import de.torpedomirror.backend.persistence.user.MirrorUser
import de.torpedomirror.backend.persistence.base.ManuallyAssignedIdEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany

@Entity
class Module(
    @Id
    @Column(name = "name", nullable = false, updatable = false)
    val name: String,

    @OneToMany(
        mappedBy = "module",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    val submodules: MutableSet<Submodule> = mutableSetOf(),

    @ManyToMany(mappedBy = "modules")
    val users: MutableSet<MirrorUser> = mutableSetOf()
) : ManuallyAssignedIdEntity<String>() {
    override fun getId(): String {
        return name
    }

    constructor(dto: CreateModuleDto): this(
        name = dto.name,
    )

    fun toCreatedDto(): CreateModuleDto {
        return CreateModuleDto(
            name = this.name,
        )
    }
}