package forge.common

import kotlinx.coroutines.flow.SharedFlow

interface AuthEventHandler {
    val eventStream: SharedFlow<AuthEvent>
    fun notify(event: AuthEvent)
    fun notifyUnauthorized() = notify(AuthEvent.Unauthorized())
    fun notifyImproveInformation() = notify(AuthEvent.ImproveInformation())
    fun notifyForbidden(@Permission permission: Int, vararg permissions: Int) =
        notify(AuthEvent.Forbidden(buildList {
            add(permission)
            if (permissions.isNotEmpty()) {
                addAll(permissions.toList())
            }
        }))
}

sealed interface AuthEvent {
    val time: Long

    data class Unauthorized(override val time: Long = System.currentTimeMillis()) : AuthEvent
    data class ImproveInformation(override val time: Long = System.currentTimeMillis()) : AuthEvent
    data class Forbidden(
        @Permission val banPermissions: List<Int>,
        override val time: Long = System.currentTimeMillis(),
    ) : AuthEvent
}