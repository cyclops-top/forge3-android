package forge.data.auth.interal

import forge.common.AuthState
import forge.common.AuthStateProvider
import forge.data.auth.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class DefaultAuthStateProvider @Inject constructor(
    private val tokenStorage: TokenStorage,
) : AuthStateProvider {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val _authStateStream = MutableStateFlow<AuthState>(AuthState.Initialization)
    override val authStateStream: StateFlow<AuthState> = _authStateStream.asStateFlow()


    private val _stateChangeEventStream = MutableSharedFlow<AuthState>()
    override val stateChangeEventStream: SharedFlow<AuthState> =
        _stateChangeEventStream.asSharedFlow()
    init {
        scope.launch {
            tokenStorage.state.map{
                if (it == null) {
                    AuthState.Unauthorized
                } else {
                    AuthState.Authorized(
                       0
                    )
                }
            }.collectLatest {
                _authStateStream.value = it
                _stateChangeEventStream.emit(it)
            }
        }
    }



    override fun currentAuthState(): AuthState {
        return _authStateStream.value
    }

}

