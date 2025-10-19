package de.torpedomirror.backend.persistence.module.stock

import de.torpedomirror.backend.dto.module.StockModuleDto
import de.torpedomirror.backend.dto.module.SubmoduleDto
import de.torpedomirror.backend.persistence.module.base.Module
import de.torpedomirror.backend.persistence.module.base.Submodule
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.time.ZonedDateTime

@Entity
class StockModule(
    module: Module,

    recordTime: ZonedDateTime,

    @OneToMany(
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "submodule_uuid")
    val quotes: MutableSet<QuoteData> = mutableSetOf(),
) : Submodule(
    module = module,
    recordTime = recordTime,
) {
    override fun toDto(): StockModuleDto = StockModuleDto(
        name = module.name,
        type = module.type,
        recordTime = recordTime,
        quotes = quotes.map {
            it.toDto()
        }
    )
}