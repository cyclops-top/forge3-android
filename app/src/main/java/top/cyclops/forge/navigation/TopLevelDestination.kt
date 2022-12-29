package top.cyclops.forge.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Star
import top.cyclops.forge.common.TextContent
import top.cyclops.forge.download.navigation.downloadRoute
import top.cyclops.forge.feature.project.navigation.projectRoute
import top.cyclops.forge.ui.IconContent
import top.cyclops.forge.ui.NavigationItem
import top.cyclops.forge.feature.download.R as downloadR
import top.cyclops.forge.feature.project.R as ProjectR


enum class TopLevelDestination(val item: NavigationItem, val route: String, val enable: Boolean) {
    Project(
        item = NavigationItem(
            icon = IconContent(Icons.Outlined.Star),
            label = TextContent(ProjectR.string.project),
        ), route = projectRoute, enable = true
    ),
    Download(
        item = NavigationItem(
            icon = IconContent(Icons.Outlined.CloudDownload),
            label = TextContent(downloadR.string.download),
        ), route = downloadRoute, enable = true
    ),
}