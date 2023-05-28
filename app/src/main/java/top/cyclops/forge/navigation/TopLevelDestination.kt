package top.cyclops.forge.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Token
import forge.common.TextContent
import forge.download.navigation.downloadRoute
import forge.feature.project.navigation.projectsRoute
import forge.ui.IconContent
import forge.ui.NavigationItem
import forge.feature.download.R as downloadR
import forge.feature.project.R as ProjectR


enum class TopLevelDestination(val item: NavigationItem, val route: String, val enable: Boolean) {
    Project(
        item = NavigationItem(
            icon = IconContent(Icons.Outlined.Token),
            label = TextContent(ProjectR.string.project),
        ), route = projectsRoute, enable = true
    ),
    Download(
        item = NavigationItem(
            icon = IconContent(Icons.Outlined.CloudDownload),
            label = TextContent(downloadR.string.download),
        ), route = downloadRoute, enable = true
    ),
}