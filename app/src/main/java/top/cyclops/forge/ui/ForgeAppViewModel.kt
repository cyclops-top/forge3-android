package top.cyclops.forge.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import forge.common.AuthStateProvider
import javax.inject.Inject

@HiltViewModel
class ForgeAppViewModel @Inject constructor(
    private val authStateProvider: AuthStateProvider
): ViewModel() {
    val isAuthed = authStateProvider.authStateStream
}