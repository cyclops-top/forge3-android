package forge.download.navigation

import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import forge.download.DownloadsScreen


const val downloadRoute = "downloads"


fun NavGraphBuilder.downloadScreen(
) {
    composable(downloadRoute) {
        DownloadsScreen()
    }
}