package forge.data.auth.interal

import forge.common.AuthEvent
import forge.common.AuthEventHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class DefaultUnauthorizedEventHandler : AuthEventHandler {
    private val _eventStream = MutableSharedFlow<AuthEvent>()
    override val eventStream = _eventStream.asSharedFlow()
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun notify(event: AuthEvent) {
        scope.launch {
            _eventStream.emit(event)
        }
    }

}