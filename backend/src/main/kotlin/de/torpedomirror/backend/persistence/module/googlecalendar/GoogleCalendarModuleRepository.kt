package de.torpedomirror.backend.persistence.module.googlecalendar

import org.springframework.data.repository.CrudRepository
import java.util.*

interface GoogleCalendarModuleRepository : CrudRepository<GoogleCalendarModule, UUID> {

}