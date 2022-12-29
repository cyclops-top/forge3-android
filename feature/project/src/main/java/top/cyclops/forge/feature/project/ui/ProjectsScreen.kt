package top.cyclops.forge.feature.project.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun ProjectsRoute(onBackClick: () -> Unit,
                           navigationToProject: (Long) -> Unit,) {
    ProjectsScreen(navigationToProject)
}


@Composable
internal fun ProjectsScreen( navigationToProject: (Long) -> Unit) {
    Column {
        Text(text = "project")
        Button(onClick = { navigationToProject(222) }) {
            Text(text = "222")
        }
    }
}