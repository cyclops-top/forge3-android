package top.cyclops.forge.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import top.cyclops.forge.navigation.TopLevelDestination

@Composable
fun rememberForgeAppState(
    navHostController: NavHostController = rememberNavController(),
    topLevelDestination: List<TopLevelDestination> = TopLevelDestination.values().asList()
): ForgeAppState {
    return remember(navHostController) {
        ForgeAppState(navHostController, topLevelDestination)
    }
}

@Suppress("MemberVisibilityCanBePrivate")
@Stable
class ForgeAppState(
    val navController: NavHostController,
    val topLevelDestinations: List<TopLevelDestination>
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = TopLevelDestination.values()
            .find { it.route == currentDestination?.route }


    fun onBackClick() {
        navController.popBackStack()
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        navController.navigate(topLevelDestination.route,topLevelNavOptions)
    }
}
