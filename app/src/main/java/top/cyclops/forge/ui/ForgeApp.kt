package top.cyclops.forge.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import top.cyclops.forge.navigation.ForgeNavHost
import top.cyclops.forge.ui.theme.ForgeTheme

@Composable
fun ForgeApp(appState: ForgeAppState = rememberForgeAppState()) {
    ForgeTheme {
        val snackbarHostState =
        Scaffold(bottomBar = { ForgeNavigationBar(appState) }) { paddingValues ->
            ForgeNavHost(
                modifier = Modifier.padding(paddingValues),
                navController = appState.navController,
                onBackClick = appState::onBackClick,
            )
        }
    }
}


@Composable
fun ForgeNavigationBar(appState: ForgeAppState) {
    var showBar by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(appState.navController.currentBackStackEntryFlow) {
        appState.navController.currentBackStackEntryFlow.mapNotNull { it.destination }
            .collectLatest { destination ->
                showBar =
                    appState.topLevelDestinations.find { it.route == destination.route } != null
            }
    }
        AnimatedVisibility(showBar,
        enter = slideInVertically { it },
            exit = slideOutVertically { it }
        ){
            NavigationBar {
                appState.topLevelDestinations.forEach { destination ->
                    NavigationBarItem(item = destination.item,
                        selected = appState.currentTopLevelDestination == destination,
                        onClick = {
                            appState.navigateToTopLevelDestination(destination)
                        })
                }
            }
        }


}