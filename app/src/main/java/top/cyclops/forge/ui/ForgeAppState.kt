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
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import top.cyclops.forge.navigation.TopLevelDestination

@Composable
fun rememberForgeAppState(
    navHostController: NavHostController = rememberAnimatedNavController(),
    topLevelDestination: List<TopLevelDestination> = TopLevelDestination.values().asList()
): ForgeAppState {
    val mainScreenNavController = rememberNavController()
    return remember(navHostController) {
        ForgeAppState(navHostController,mainScreenNavController, topLevelDestination)
    }
}

@Suppress("MemberVisibilityCanBePrivate")
@Stable
class ForgeAppState(
    val navController: NavHostController,
    val bottomScreenNavController: NavHostController,
    val topLevelDestinations: List<TopLevelDestination>
) {
    val currentBottomDestination: NavDestination?
        @Composable get() = bottomScreenNavController
            .currentBackStackEntryAsState().value?.destination


    fun onBackClick() {
        navController.popBackStack()
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(bottomScreenNavController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        bottomScreenNavController.navigate(topLevelDestination.route,topLevelNavOptions)
    }
}
