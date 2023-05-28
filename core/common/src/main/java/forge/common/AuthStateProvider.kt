package forge.common

import androidx.annotation.IntDef
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthStateProvider {
    val stateChangeEventStream: SharedFlow<AuthState>
    val authStateStream: StateFlow<AuthState>
    fun currentAuthState(): AuthState
}

sealed interface AuthState {
    val isAuthorized get() = this is Authorized

    object Initialization : AuthState
    object Unauthorized : AuthState
    class Authorized private constructor(
        /**
         * @see [Permission]
         */
        private val bans: Int = 0,
    ) : AuthState {
        fun hasPermission(@Permission permission: Int): Boolean {
            return (bans and permission) == 0
        }

        companion object {
            operator fun invoke(
                @Permission vararg bans: Int,
            ): Authorized {
                val ban = bans.takeIf { it.isNotEmpty() }?.reduce { acc, i ->
                    acc or i
                } ?: 0
                return Authorized(ban)
            }
        }
    }
}


@IntDef(value = [Permission.Download, Permission.Preview, Permission.Upload])
annotation class Permission {
    companion object {
        /**
         * 交易
         */
        const val Download = 0b0000_0000_0000_0001

        /**
         * 发朋友圈
         */
        const val Preview = 0b0000_0000_0000_0010

        /**
         * 评价
         */
        const val Upload = 0b0000_0000_0000_0100
    }
}