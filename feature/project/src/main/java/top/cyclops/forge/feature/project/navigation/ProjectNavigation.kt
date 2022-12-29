package top.cyclops.forge.feature.project.navigation

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import top.cyclops.forge.feature.project.ui.ProjectRoute
import top.cyclops.forge.feature.project.ui.ProjectsRoute

@VisibleForTesting
internal const val projectIdArg = "projectId"

const val projectRoute = "projects"


fun NavController.navigateToProject(projectId: Long) {
    navigate("project/$projectId")
}

internal class ProjectArgs(val projectId: Long) {
    companion object {
        operator fun invoke(savedStateHandle: SavedStateHandle): ProjectArgs {
            return ProjectArgs(savedStateHandle[projectIdArg]!!)
        }
    }
}

fun NavGraphBuilder.projectsScreen(
    navController: NavHostController,
    onBackClick: () -> Unit,
) {
    composable(projectRoute) {
        ProjectsRoute(onBackClick,navController::navigateToProject)
    }
    composable(route = "project/{$projectIdArg}",
        arguments = listOf(
            navArgument(projectIdArg) { type = NavType.LongType }
        )
    ) {
        ProjectRoute(onBackClick)
    }
}