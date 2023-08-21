package forge.feature.project.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import forge.data.project.ProjectRepository
import forge.data.version.VersionRepository
import forge.feature.project.navigation.ProjectArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val versionRepository: VersionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val args = ProjectArgs(savedStateHandle)
    private val _projectStream = MutableStateFlow(args.project)
    val projectStream = _projectStream.asStateFlow()

    val versions = versionRepository.versions(args.projectId)
        .cachedIn(viewModelScope)

    init {
        if (args.project == null) {
            viewModelScope.launch {
                projectRepository.detailStream(args.projectId).collectLatest {
                    _projectStream.value = it
                }
            }
        }
    }


    fun collect(id: Long) {
        viewModelScope.launch {
            projectRepository.collect(id)
        }
    }

    fun cancelCollect(id: Long) {
        viewModelScope.launch {
            projectRepository.cancelCollect(id)
        }
    }
}

