package top.cyclops.forge.feature.project.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.cyclops.forge.feature.project.navigation.ProjectArgs
import javax.inject.Inject

@HiltViewModel
internal class ProjectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val args = ProjectArgs(savedStateHandle)
}