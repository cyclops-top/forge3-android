package forge.feature.project.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import forge.feature.project.ui.ProjectRoute
import forge.feature.project.ui.ProjectsRoute
import forge.model.Project

@VisibleForTesting
internal const val projectIdArg = "projectId"
internal const val projectArg = "project"

const val projectsRoute = "projects"


fun NavController.navigateToProject(projectId: Long) {
    navigate("project/$projectId")
}

fun NavController.navigateToProject(project: Project) {
    navigate("project/${project.id}")
}

internal class ProjectArgs(val projectId: Long,val project: Project?) {
    companion object {
        operator fun invoke(savedStateHandle: SavedStateHandle): ProjectArgs {
            return ProjectArgs(savedStateHandle[projectIdArg]!!, savedStateHandle[projectArg])
        }
    }
}

fun NavGraphBuilder.projectsScreen(
    navController: NavHostController,
) {
    composable(projectsRoute) {
        ProjectsRoute(navController::navigateToProject)
    }
}


fun NavGraphBuilder.projectScreen(
    navController: NavHostController,
    onBackClick: () -> Unit,
) {
    composable(route = "project/{$projectIdArg}",
        arguments = listOf(
            navArgument(projectIdArg) { type = NavType.LongType },
            navArgument(projectArg) {
                type = NavType.ParcelableType(Project::class.java)
                nullable = true
            }
        )
    ) {
        ProjectRoute(onBackClick)
    }
}