package top.cyclops.forge.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import forge.common.AuthState
import forge.download.navigation.DownloadRoute
import forge.download.navigation.downloadRoute
import forge.feature.auth.navigation.signInRoute
import forge.feature.project.navigation.navigateToProject
import forge.feature.project.navigation.projectsRoute
import forge.feature.project.ui.ProjectsRoute
import forge.ui.NavigationBarItem
import forge.ui.theme.ForgeTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import top.cyclops.forge.navigation.ForgeNavHost

@Composable
fun ForgeApp(appState: ForgeAppState = rememberForgeAppState()) {
    val forgeAppViewModel = hiltViewModel<ForgeAppViewModel>()
    LaunchedEffect(key1 = forgeAppViewModel) {
        forgeAppViewModel.isAuthed.filter { it is AuthState.Unauthorized }.collectLatest {
            appState.navController.navigate(signInRoute)
        }
    }
    ForgeTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ForgeNavHost(
                navController = appState.navController,
                onBackClick = appState::onBackClick,
            ) {
                MainScreen(appState)
            }
        }
    }
}


@Composable
fun MainScreen(
    appState: ForgeAppState,
    modifier: Modifier = Modifier,
) {
    Scaffold(bottomBar = {
        NavigationBar {
            appState.topLevelDestinations.forEach { destination ->
                NavigationBarItem(item = destination.item,
                    selected = appState.currentBottomDestination?.route == destination.route,
                    onClick = {
                        appState.navigateToTopLevelDestination(destination)
                    })
            }
        }
    }) { paddingValues ->
        NavHost(
            modifier = modifier.padding(paddingValues),
            navController = appState.bottomScreenNavController,
            startDestination = projectsRoute,
        ) {
            composable(projectsRoute) {
                ProjectsRoute(appState.navController::navigateToProject)
            }
            composable(downloadRoute){
                DownloadRoute()
            }
        }
    }
}