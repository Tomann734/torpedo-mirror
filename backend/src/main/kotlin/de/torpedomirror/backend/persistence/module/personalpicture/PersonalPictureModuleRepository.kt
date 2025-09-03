package de.torpedomirror.backend.persistence.module.personalpicture

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface PersonalPictureModuleRepository : CrudRepository<PersonalPictureModule, UUID> {
}