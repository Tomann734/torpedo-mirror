package de.torpedomirror.backend.service

import de.torpedomirror.backend.event.ModuleAddedToUserEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.time.ZonedDateTime

@Component
class ApplicationEventListener(
    private val submoduleService: SubmoduleService
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun handleModuleAddedToUserEvent(event: ModuleAddedToUserEvent) {
        submoduleService.createModule(
            moduleName = event.moduleName,
            now = ZonedDateTime.now(),
        )
    }
}