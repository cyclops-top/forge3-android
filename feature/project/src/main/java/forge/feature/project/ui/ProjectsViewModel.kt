package forge.feature.project.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import forge.data.project.ProjectRepository
import kotlinx.coroutines.launch
import top.cyclops.forge.statistics.StatisticsEvent
import javax.inject.Inject

@HiltViewModel
internal class ProjectsViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val statisticsEvent: StatisticsEvent,
) :
    ViewModel() {
    val projects = projectRepository.projects().cachedIn(viewModelScope)


    fun collect(id: Long) {
        statisticsEvent.event("project-collect")
        viewModelScope.launch {
            projectRepository.collect(id)
        }
    }

    fun cancelCollect(id: Long) {
        statisticsEvent.event("project-cancel-collect")
        viewModelScope.launch {
            projectRepository.cancelCollect(id)
        }
    }
}