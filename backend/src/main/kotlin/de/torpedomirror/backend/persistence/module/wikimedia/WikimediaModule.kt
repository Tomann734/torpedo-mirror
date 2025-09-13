package de.torpedomirror.backend.persistence.module.wikimedia

import de.torpedomirror.backend.dto.module.WikimediaModuleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.time.ZonedDateTime

@Entity
class WikimediaModule(
    module: Module,

    recordTime: ZonedDateTime,

    @OneToMany(
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "submodule_uuid")
    val facts: MutableSet<WikimediaFact>
) : Submodule(
    module = module,
    recordTime = recordTime,
) {
    override fun toDto() = WikimediaModuleDto(
        name = this.module.name,
        type = this.module.type,
        recordTime = this.recordTime,
        facts = this.facts.map { it.toDto() }
    )
}