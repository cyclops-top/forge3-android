package top.cyclops.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.cyclops.data.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _signInResult = MutableSharedFlow<Result<Unit>>()
    val signInResult = _signInResult.asSharedFlow()

    private val _state = MutableStateFlow(SignInState("justin", "123456"))
    val state = _state.asStateFlow()


    fun updateUsername(username: String) {
        _state.update {
            it.copy(username = username)
        }
    }

    fun updatePassword(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            val (username, password) = _state.value
            _signInResult.emit(
                authRepository.signIn(username, password).also {
                    println(it)
                }
            )
        }
    }

    fun current() {
        viewModelScope.launch {
            authRepository.currentAccount().also {
                println(it)
            }
        }
    }

}