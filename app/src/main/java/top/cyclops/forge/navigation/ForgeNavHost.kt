package top.cyclops.forge.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import forge.feature.auth.navigation.authRoute
import forge.feature.project.navigation.projectScreen


@Composable
fun ForgeNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    mainScreen: @Composable () -> Unit,
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "main",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400),
                targetOffset = { (it * 0.3).toInt() }
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400),
                initialOffset = { (it * 0.3).toInt() }
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
    ) {
        composable("main") {
            mainScreen()
        }
        projectScreen(navController, onBackClick)
        authRoute(onBackClick)
    }
}

