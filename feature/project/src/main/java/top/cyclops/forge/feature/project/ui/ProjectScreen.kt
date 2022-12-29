package top.cyclops.forge.feature.project.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ProjectRoute(
    onBackClick: () -> Unit,
    viewModel: ProjectViewModel = hiltViewModel()
) {
    val id = viewModel.args
    ProjectScreen(id.projectId)
}

@Composable
fun ProjectScreen(project: Long) {
    Column {
        Text(text = "---->$project")
    }
}