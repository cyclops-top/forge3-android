package top.cyclops.forge.download.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import top.cyclops.forge.download.DownloadsScreen


const val downloadRoute = "downloads"


fun NavGraphBuilder.downloadScreen(
    onBackClick: () -> Unit
) {
    composable(downloadRoute) {
        DownloadsScreen()
    }
}