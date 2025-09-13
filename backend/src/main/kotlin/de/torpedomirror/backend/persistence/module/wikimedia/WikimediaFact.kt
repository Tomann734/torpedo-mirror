package de.torpedomirror.backend.persistence.module.wikimedia

import de.torpedomirror.backend.dto.module.wikimedia.WikimediaFactDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
class WikimediaFact(
    @Column(name = "description", nullable = false, updatable = false)
    val description: String,

    @Column(name = "year", nullable = false, updatable = false)
    val year: Int,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID? = null

    fun toDto() = WikimediaFactDto(
        description = description,
        year = year,
    )
}