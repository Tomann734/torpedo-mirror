package de.torpedomirror.backend.persistence.user

import de.torpedomirror.backend.dto.CreateMirrorUserDto
import de.torpedomirror.backend.persistence.base.ManuallyAssignedIdEntity
import de.torpedomirror.backend.persistence.module.base.Module
import jakarta.persistence.*

@Entity
class MirrorUser(
    @Id
    @Column(name = "username", nullable = false, updatable = false)
    val username: String,

    @ManyToMany
    @JoinTable(
        name = "user_module",
        joinColumns = [JoinColumn(name = "username")],
        inverseJoinColumns = [JoinColumn(name = "module_name")]
    )
    val modules: MutableSet<Module> = mutableSetOf()
) : ManuallyAssignedIdEntity<String>() {
    override fun getId(): String {
        return this.username
    }

    fun toCreatedDto() = CreateMirrorUserDto(
        username = this.username,
    )
}