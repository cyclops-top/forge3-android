package forge.download.navigation

import androidx.compose.runtime.Composable
import forge.download.DownloadsScreen
import forge.ui.statisticsView


const val downloadRoute = "downloads"


@Composable
fun DownloadRoute(
) {
        statisticsView(downloadRoute)
        DownloadsScreen()
}