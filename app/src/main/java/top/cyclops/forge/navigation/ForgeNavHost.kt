package top.cyclops.forge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import top.cyclops.feature.auth.navigation.authRoute
import top.cyclops.feature.auth.navigation.signInRoute
import top.cyclops.forge.download.navigation.downloadScreen
import top.cyclops.forge.feature.project.navigation.projectsScreen


@Composable
fun ForgeNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = signInRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        projectsScreen(navController, onBackClick)
        downloadScreen(onBackClick)
        authRoute()
    }
}